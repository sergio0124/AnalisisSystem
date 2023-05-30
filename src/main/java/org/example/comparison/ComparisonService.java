package org.example.comparison;

import lombok.AllArgsConstructor;
import org.example.book.BookDTO;
import org.example.book.BookMapping;
import org.example.book.BookRepository;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineDTO;
import org.example.discipline.DisciplineRepository;
import org.example.user.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class ComparisonService {

    ComparisonRepository comparisonRepository;
    ComparisonMapping comparisonMapping;
    BookMapping bookMapping;
    DisciplineRepository disciplineRepository;
    BookRepository bookRepository;
    UserRepository userRepository;

    public ComparisonDTO getComparisonById(Long id) {
        Comparison comparison = comparisonRepository.findById(id).orElse(null);
        if (comparison != null) {
            return comparisonMapping.mapToComparisonDTO(comparison);
        } else {
            return null;
        }
    }

    public void deleteComparisonByDisciplineIdAndBookId(String disciplineId, Long bookId) {
        Comparison comparison = comparisonRepository.findComparisonByDisciplineIdContainsAndBookId(disciplineId, bookId);
        if (comparison != null) {
            comparisonRepository.delete(comparison);
        }
    }

    public void updateComparison(ComparisonDTO comparisonDTO) {
        Comparison comparison = comparisonRepository.findById(comparisonDTO.getId()).orElse(null);
        if (comparison == null) {
            return;
        }
        comparison.setMark(comparisonDTO.getMark());
        comparison.setType(ComparisonType.MANUAL);
        comparison.setDate(comparisonDTO.getDate());
        comparison.setDescription(comparisonDTO.getDescription());
        comparison.setUser(userRepository.findByUsername(comparisonDTO.getUsername()).orElse(null));
        comparisonRepository.save(comparison);
    }

    public List<BookDTO> loadAllBooksByDisciplineId(String disciplineId) {
        String discPlanId = disciplineRepository
                .findDisciplinesByIdContaining(disciplineId).get(0)
                .getAcademicPlanDisciplineId();
        return comparisonRepository
                .findComparisonsByDiscipline_AcademicPlanDisciplineIdContains(discPlanId)
                .stream()
                .filter(c -> !c.discipline.getId().contains(disciplineId))
                .map(Comparison::getBook)
                .distinct()
                .map(bookMapping::mapToBookDTO)
                .toList();
    }

    public void createComparison(BookDTO bookDTO, DisciplineDTO disciplineDTO) {
        Comparison comparison = new Comparison();
        comparison.setType(ComparisonType.AUTO);
        comparison.setDate(new Timestamp(new Date().getTime()));
        comparison.setDiscipline(disciplineRepository.findDisciplinesByIdContaining(disciplineDTO.getId()).get(0));
        comparison.setBook(bookRepository.findById(bookDTO.getId()).orElse(null));
        if (comparison.getBook() == null || comparison.getDiscipline() == null) {
            return;
        }
        setMarkAndDescription(bookDTO, comparison, disciplineDTO);
    }

    private void setMarkAndDescription(BookDTO bookDTO, Comparison comparison, DisciplineDTO disciplineDTO){
        int mark = 0;
        StringBuilder stringBuilder = new StringBuilder();
        if (bookDTO.getName().contains(disciplineDTO.getAcademicPlanDisciplineName()) ||
                bookDTO.getAnnotation().contains(disciplineDTO.getAcademicPlanDisciplineName())) {
            mark += 5;
            stringBuilder.append("В результате поиска по аннотации и названию было найдено название дисциплины: +5 к оценке.\n");
        } else {
            stringBuilder.append("В результате поиска по аннотации и названию было не было найдено название дисциплины. \n");
            String[] parts = disciplineDTO
                    .getAcademicPlanDisciplineName()
                    .toLowerCase()
                    .replaceAll("[^a-zA-ZА-Яа-я]", "")
                    .split(" ");
            float standCount = (float) disciplineDTO
                    .getAcademicPlanDisciplineName().length() / 7;
            int count = 0;
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].substring(0, parts[i].length() - 2);
            }
            for (String part : parts) {
                Pattern p = Pattern.compile(part);
                Matcher m = p.matcher(bookDTO.getAnnotation());
                while (m.find()) {
                    count++;
                }
                m = p.matcher(bookDTO.getIntroduction());
                while (m.find()) {
                    count++;
                }
            }
            if (standCount / 20 < count) {
                mark += 5;
                stringBuilder.append("В результате поиска по введению и аннотации ключевые слова встречаются часто: +5 к оценке.\n");
            } else if (standCount / 50 < count) {
                mark += 4;
                stringBuilder.append("В результате поиска по введению и аннотации ключевые слова встречаются достаточно часто: +4 к оценке.\n");
            } else if (standCount / 80 < count) {
                mark += 3;
                stringBuilder.append("В результате поиска по введению и аннотации ключевые слова встречаются периодично: +3 к оценке.\n");
            } else if (count > 0) {
                mark += 2;
                stringBuilder.append("При анализе введения и аннотации была выявлена косвенная связь: +2 к оценке.\n");
            } else {
                stringBuilder.append("Анализ введения и аннотации не выявил корелляций.\n");
            }
        }
        Date creationDate = new Date(bookDTO.getCreationDate().getTime());
        long yearsDifference = ChronoUnit.YEARS.between(LocalDateTime.of(creationDate.getYear(),
                creationDate.getMonth() + 1, creationDate.getDay() + 1, creationDate.getHours(), creationDate.getMinutes()), LocalDateTime.now());
        if (yearsDifference > 10) {
            mark -= 3;
            stringBuilder.append("Книга написана более 10 лет назад: -3 к оценке.\n");
        } else if (yearsDifference >= 7) {
            mark -= 2;
            stringBuilder.append("Книга написана более 7 лет назад: -2 к оценке.\n");
        } else if (yearsDifference >= 4) {
            mark -= 1;
            stringBuilder.append("Книга написана более 4 лет назад: -1 к оценке.\n");
        } else {
            stringBuilder.append("Книга написана менее 4 лет назад: штрафов к оценке не предусмотрено.\n");
        }

        comparison.setDescription(stringBuilder.toString());
        if (mark < 0) {
            comparison.setMark(0);
        } else {
            comparison.setMark(mark);
        }
        comparisonRepository.save(comparison);
    }
}
