package org.example.book;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import lombok.AllArgsConstructor;
import org.example.comparison.Comparison;
import org.example.comparison.ComparisonRepository;
import org.example.comparison.ComparisonService;
import org.example.comparison.ComparisonType;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineDTO;
import org.example.discipline.DisciplineRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    ComparisonRepository comparisonRepository;
    ComparisonService comparisonService;

    public BookDTO findBookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        return book != null ?
                bookMapping.mapToBookDTO(book)
                : null;
    }

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

    public void updateBook(BookDTO bookDTO){
        Book book = bookRepository.findById(bookDTO.getId()).orElse(null);
        if (book == null) {
            return;
        }
        book.setAnnotation(bookDTO.getAnnotation());
        book.setAuthor(bookDTO.getAuthor());
        book.setUrl(bookDTO.getUrl());
        book.setName(bookDTO.getName());
        book.setCreationDate(bookDTO.getCreationDate());
        book.setIntroduction(bookDTO.getIntroduction());
        book.setPages(bookDTO.getPages());
        bookRepository.save(book);
    }

    public BookDTO parseBookByPdf(String url) {
        String fileName = url.replaceAll("[^a-zA-Z0-9]", "a") + ".pdf";
        BookDTO bookDTO = new BookDTO();
        try {
            PdfReader reader = loadPdf(url, fileName);
            String firstPage = PdfTextExtractor
                    .getTextFromPage(reader, 2, new SimpleTextExtractionStrategy());

            // searching string like as characteristic in search
            String fullNameAndOther;
            Pattern pagePattern = Pattern.compile("[0-9]{1,3} с");
            Matcher pageMatcher = pagePattern.matcher(firstPage);
            int pageIndex = 0;
            if (pageMatcher.find()) {
                pageIndex = pageMatcher.start();
            }
            Pattern spacePattern = Pattern.compile("\n");
            Matcher spaceMatcher = spacePattern.matcher(firstPage);
            int spaceIndex = 0;
            while (spaceIndex < pageIndex && spaceMatcher.find()) {
                if (spaceMatcher.start() < pageIndex) {
                    spaceIndex = spaceMatcher.start();
                } else {
                    break;
                }
            }
            fullNameAndOther = firstPage.substring(spaceIndex, pageIndex);
            bookDTO.setName(fullNameAndOther.split("/")[0]);
            if (bookRepository.findBookByNameContainingIgnoreCase(bookDTO.getName()) != null) {
                return null;
            }

            // getting name
            bookDTO.setName(fullNameAndOther.split("/")[0]);
            if (bookRepository.findBookByNameContainingIgnoreCase(bookDTO.getName()) != null) {
                return null;
            }

            // getting info about author
            String author = fullNameAndOther.split("/")[1];
            Pattern pattern = Pattern.compile("[0-9]{1,3} с");
            Matcher matcher = pattern.matcher(author);
            if (matcher.find()) {
                bookDTO.setAuthor(author.substring(0, matcher.start() - 2));
                try {
                    bookDTO.setPages(
                            Integer.parseInt(
                                    matcher.group(0).replaceAll("[^0-9]", "")
                            )
                    );
                } catch (Exception exception) {
                    bookDTO.setPages(0);
                }

            } else {
                bookDTO.setAuthor(author);
            }

            // getting data from author
            String data = bookDTO.getAuthor().replaceAll("[^0-9]", "");
            if (matcher.find()) {
                try {
                    bookDTO.setCreationDate(
                            new Timestamp(
                                    new SimpleDateFormat("yyyy")
                                            .parse(data)
                                            .getTime()
                            )
                    );
                } catch (ParseException e) {
                    bookDTO.setCreationDate(new Timestamp(new Date().getTime()));
                }
            }

            // getting annotation from pdf
            Pattern startAnno = Pattern.compile("[0-9]{1,3} с.");
            Pattern endAnno = Pattern.compile("УДК");
            Matcher startAnnoMatcher = startAnno.matcher(firstPage);
            Matcher endAnnoMatcher = endAnno.matcher(firstPage);
            if (startAnnoMatcher.find()) {
                if (endAnnoMatcher.find()) {
                    endAnnoMatcher.find();
                    bookDTO.setAnnotation(firstPage.substring(
                            startAnnoMatcher.end() + 2, endAnnoMatcher.start() - 1
                    ));
                }
            }

            // trying to get introduction
            startAnno = Pattern.compile("Введение|ВВЕДЕНИЕ");
            endAnno = Pattern.compile("1\\.");
            Pattern altEndAnno = Pattern.compile("\n\n");
            Pattern including = Pattern.compile("Содержание|СОДЕРЖАНИЕ|Оглавление|ОГЛАВЛЕНИЕ");
            int pageNumber = 3;
            boolean overIntro = false;
            boolean over = false;
            StringBuilder stringBuilder = new StringBuilder();
            while (pageNumber <= 10 && !over) {
                String curPage = PdfTextExtractor
                        .getTextFromPage(reader, pageNumber, new SimpleTextExtractionStrategy());
                Matcher incMatch = including.matcher(curPage);
                startAnnoMatcher = startAnno.matcher(curPage);
                endAnnoMatcher = endAnno.matcher(curPage);
                Matcher altEndMatcher = altEndAnno.matcher(curPage);
                int startPos = 0;
                int endPos = curPage.length();
                if (startAnnoMatcher.find() && !overIntro) {
                    if (!incMatch.find() || incMatch.start() > 100) {
                        overIntro = true;
                        startPos = startAnnoMatcher.end();
                    }
                }
                if (endAnnoMatcher.find() && overIntro) {
                    int end = endAnnoMatcher.start();
                    if (startPos < end) {
                        over = true;
                        endPos = endAnnoMatcher.start() - 1;
                    }
                } else if (altEndMatcher.find() && overIntro){
                    int end = altEndMatcher.start();
                    if (startPos < end) {
                        over = true;
                        endPos = altEndMatcher.start() - 1;
                    }
                }
                if (overIntro) {
                    stringBuilder.append(curPage, startPos, endPos);
                }
                pageNumber++;
            }
            bookDTO.setIntroduction(stringBuilder.toString());
        } catch (IOException e) {
            return null;
        }
        File pdfFile = new File(fileName);
        pdfFile.delete();
        return bookDTO;
    }

    public BookDTO parseBookFromVenec(BookDTO bookDTO) {
        String info = bookDTO.getName();
        // getting name
        bookDTO.setName(info.split("/")[0]);
        if (bookRepository.findBookByNameContainingIgnoreCase(bookDTO.getName()) != null) {
            return null;
        }

        // getting data
        Pattern pattern = Pattern.compile("Дата создания: [0-9]{2}\\.[0-9]{2}\\.[0-9]{4}");
        Matcher matcher = pattern.matcher(info);
        if (matcher.find()) {
            try {
                bookDTO.setCreationDate(
                        new Timestamp(
                                new SimpleDateFormat("ddMMyyyy")
                                        .parse(matcher.group()
                                                .replaceAll("[^0-9]", ""))
                                        .getTime()
                        )
                );
            } catch (ParseException e) {
                bookDTO.setCreationDate(new Timestamp(new Date().getTime()));
            }
        }

        // getting author
        String author = info.split("/")[1];
        pattern = Pattern.compile("[0-9]{1,3} с");
        matcher = pattern.matcher(author);
        if (matcher.find()) {
            bookDTO.setAuthor(author.substring(0, matcher.start() - 2));
            try {
                bookDTO.setPages(
                        Integer.parseInt(
                                matcher.group(0).replaceAll("[^0-9]", "")
                        )
                );
            } catch (Exception exception) {
                bookDTO.setPages(0);
            }

        } else {
            bookDTO.setAuthor(author);
        }

        // anno and intro
        String fileName = bookDTO.getUrl().replaceAll("[^a-zA-Z0-9]", "a") + ".pdf";
        try {
            PdfReader reader = loadPdf(findUrlBySuburl(bookDTO.url), fileName);
            String firstPage = PdfTextExtractor
                    .getTextFromPage(reader, 2, new SimpleTextExtractionStrategy());

            // getting anno
            Pattern startAnno = Pattern.compile("[0-9]{1,3} с.");
            Pattern endAnno = Pattern.compile("УДК");
            Matcher startAnnoMatcher = startAnno.matcher(firstPage);
            Matcher endAnnoMatcher = endAnno.matcher(firstPage);
            if (startAnnoMatcher.find()) {
                if (endAnnoMatcher.find()) {
                    endAnnoMatcher.find();
                    bookDTO.setAnnotation(firstPage.substring(
                            startAnnoMatcher.end() + 2, endAnnoMatcher.start() - 1
                    ));
                }
            }

            // getting intro
            startAnno = Pattern.compile("Введение|ВВЕДЕНИЕ");
            endAnno = Pattern.compile("1\\.");
            Pattern altEndAnno = Pattern.compile("\n\n");
            Pattern including = Pattern.compile("Содержание|СОДЕРЖАНИЕ|Оглавление|ОГЛАВЛЕНИЕ");
            int pageNumber = 3;
            boolean overIntro = false;
            boolean over = false;
            StringBuilder stringBuilder = new StringBuilder();
            while (pageNumber <= 10 && !over) {
                String curPage = PdfTextExtractor
                        .getTextFromPage(reader, pageNumber, new SimpleTextExtractionStrategy());
                Matcher incMatch = including.matcher(curPage);
                startAnnoMatcher = startAnno.matcher(curPage);
                endAnnoMatcher = endAnno.matcher(curPage);
                Matcher altEndMatcher = altEndAnno.matcher(curPage);
                int startPos = 0;
                int endPos = curPage.length();
                if (startAnnoMatcher.find() && !overIntro) {
                    if (!incMatch.find() || incMatch.start() > 100) {
                        overIntro = true;
                        startPos = startAnnoMatcher.end();
                    }
                }
                if (endAnnoMatcher.find() && overIntro) {
                    int end = endAnnoMatcher.start();
                    if (startPos < end) {
                        over = true;
                        endPos = endAnnoMatcher.start() - 1;
                    }
                } else if (altEndMatcher.find() && overIntro){
                    int end = altEndMatcher.start();
                    if (startPos < end) {
                        over = true;
                        endPos = altEndMatcher.start() - 1;
                    }
                }
                if (overIntro) {
                    stringBuilder.append(curPage, startPos, endPos);
                }
                pageNumber++;
            }
            bookDTO.setIntroduction(stringBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File pdfFile = new File(fileName);
        pdfFile.delete();
        return bookDTO;
    }

    public void saveBook(BookDTO bookDTO, String disciplineId) {
        bookDTO = parseBookFromVenec(bookDTO);
        if (bookDTO == null) {
            return;
        }

        Book book = bookMapping.mapToBookEntity(bookDTO);
        book = bookRepository.save(book);
        Discipline discipline = disciplineRepository.findDisciplineByIdContaining(disciplineId);

        Comparison comparison = new Comparison();
        comparison.setBook(book);
        comparison.setDiscipline(discipline);
        comparison.setDate(new Timestamp(new Date().getTime()));
        comparison.setMark(0);
        comparison.setType(ComparisonType.AUTO);
        comparison.setDescription(" - ");

        comparisonRepository.save(comparison);
    }

    private String findUrlBySuburl(String sub_url) throws IOException {
        URL url = new URL(sub_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
            in.close();
            Pattern pattern = Pattern.compile("\\./disk/[0-9]{4}/[a-zA-Z0-9а-яА-Я]*\\.pdf");
            Matcher matcher = pattern.matcher(response.toString());
            if (matcher.find()) {
                return matcher.group().replaceFirst("\\.", "http://lib.ulstu.ru/venec");
            } else {
                return null;
            }

        }
        return null;
    }

    private PdfReader loadPdf(String file_url, String fileName) throws IOException {
        URL url = new URL(file_url);
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

    public void deleteBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            bookRepository.delete(book);
        }
    }
}
