package org.example.book;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import lombok.AllArgsConstructor;
import org.example.comparison.Comparison;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineDTO;
import org.example.discipline.DisciplineRepository;
import org.example.discipline.DisciplineService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class BookService {
    BookRepository bookRepository;
    BookMapping bookMapping;
    DisciplineRepository disciplineRepository;

    private List<BookDTO> findBooksBySelectAndString(String search, String select, WebDriver driver) {
        driver.get("http://venec.ulstu.ru/lib/");
        WebElement searchInput = driver.findElement(By.name("q"));
        searchInput.sendKeys(search);
        new Select(driver.findElement(By.name("msk"))).selectByVisibleText(select);
        driver.findElement(By.xpath("//input[@value='Найти']")).click();

        List<WebElement> bookElements = driver.findElements(By.xpath("//td[@align='justify']"));
        List<BookDTO> books = new LinkedList<>();
        bookElements.forEach(tr -> {
            String visibleText = tr.getText();
            BookDTO book = new BookDTO();
            book.setName(visibleText);
            book.setUrl(tr.findElement(By.xpath(".//a")).getAttribute("href"));
            books.add(book);
        });
        return books;
    }

    public List<BookDTO> findBooks(DisciplineDTO disciplineDTO) {
        System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        String search = disciplineDTO
                .getAcademicPlanDisciplineName()
                .replaceAll("[^а-яА-Я ]", "")
                .replaceAll(" ", "&");

        List<BookDTO> books = new LinkedList<>();
        books.addAll(findBooksBySelectAndString(search, "название", driver));
        books.addAll(findBooksBySelectAndString(search, "аннотация", driver));

        driver.close();
        return books;
    }

    public void saveBook(BookDTO bookDTO, String disciplineId) {
        String info = bookDTO.getName();
        bookDTO.setName(info.split("/")[0]);

        Pattern pattern = Pattern.compile("Дата создания: .{10};");
        Matcher matcher = pattern.matcher(info);
        if (matcher.find()) {
            try {
                bookDTO.setCreationDate(
                        new Timestamp(
                                new SimpleDateFormat("dd/MM/yyyy")
                                        .parse(matcher.group()
                                                .split("[?:]")[1]
                                                .trim())
                                        .getTime()
                        )
                );
            } catch (ParseException e) {
                bookDTO.setCreationDate(new Timestamp(new Date().getTime()));
            }
        }

        String author = info.split("/")[1];
        pattern = Pattern.compile("[0-9]{1,3}с");
        matcher = pattern.matcher(author);
        if (matcher.find()) {
            bookDTO.setAuthor(author.substring(0, matcher.start() - 3));
            bookDTO.setPages(
                    Integer.parseInt(
                            matcher.group(0).replaceAll("[^0-9]", "")
                    )
            );
        } else {
            bookDTO.setAuthor(author);
        }

        try {
            String fileName = bookDTO.getUrl().replaceAll("[^a-zA-Z0-9]", "a") + ".pdf";
            PdfReader reader = loadPdf(bookDTO.url, fileName);
            String firstPage = PdfTextExtractor
                    .getTextFromPage(reader, 2, new SimpleTextExtractionStrategy());

            Pattern startAnno = Pattern.compile("[0-9]{1,3}с.");
            Pattern endAnno = Pattern.compile("УДК");
            Matcher startAnnoMatcher = startAnno.matcher(author);
            Matcher endAnnoMatcher = endAnno.matcher(author);
            if (startAnnoMatcher.find()) {
                if (endAnnoMatcher.find()) {
                    bookDTO.setAnnotation(firstPage.substring(
                            startAnnoMatcher.end() + 2, endAnnoMatcher.start() - 1
                    ));
                }
            }

            startAnno = Pattern.compile("Введение|ВВЕДЕНИЕ");
            endAnno = Pattern.compile("1\\.");
            startAnnoMatcher = startAnno.matcher(author);
            endAnnoMatcher = endAnno.matcher(author);
            int pageNumber = 3;
            boolean overIntro = false;
            boolean over = false;
            StringBuilder stringBuilder = new StringBuilder();
            while (pageNumber <= 10 && !over) {
                String curPage = PdfTextExtractor
                        .getTextFromPage(reader, pageNumber, new SimpleTextExtractionStrategy());
                int startPos = 0;
                int endPos = curPage.length();
                if (startAnnoMatcher.find() && !overIntro) {
                    overIntro = true;
                    startPos = startAnnoMatcher.end();
                }
                if (endAnnoMatcher.find()) {
                    over = true;
                    endPos = endAnnoMatcher.start() - 1;
                }
                stringBuilder.append(curPage.substring(startPos, endPos));
                pageNumber++;
            }
            bookDTO.setIntroduction(stringBuilder.toString());

            File pdfFile = new File(fileName);
            pdfFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Book book = bookMapping.mapToBookEntity(bookDTO);
        Discipline discipline = disciplineRepository.findDisciplineByIdContaining(disciplineId);
        Comparison comparison = new Comparison();
        comparison.setBook(book);
        comparison.setDiscipline(discipline);
        book.setComparisons(List.of(comparison));
        bookRepository.save(book);
    }

    private PdfReader loadPdf(String url1, String fileName) throws IOException {
        URL url = null;
        URL obj = new URL(url1);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            Pattern pattern = Pattern.compile("\\./disk/[0-9]{4}/[a-zA-Z0-9а-яА-Я]*\\.pdf");
            Matcher matcher = pattern.matcher(response.toString());
            if (matcher.find()) {
                url = new URL(matcher.group().replaceFirst("\\.", "http://lib.ulstu.ru/venec"));
            }
            in.close();
        }

        InputStream in = url.openStream();
        FileOutputStream fos = new FileOutputStream(fileName);

        int length = -1;
        byte[] buffer = new byte[1024];
        while ((length = in.read(buffer)) > -1) {
            fos.write(buffer, 0, length);
        }
        fos.close();
        in.close();

        PdfReader reader = new PdfReader(fileName);
        return reader;
    }
}
