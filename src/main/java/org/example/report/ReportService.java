package org.example.report;

import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.example.discipline.*;
import org.example.plan.PlanRepository;
import org.example.result.DisciplineResultDTO;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {
    DisciplineRepository disciplineRepository;
    DisciplineMapping disciplineMapping;
    PlanRepository planRepository;

    public List<DisciplineDTO> getDisciplinesByPlanId(String academicPlanId) {
        List<DisciplineDTO> disciplineDTOS = mapToDisciplineDTO(
                disciplineRepository.findDisciplineByAcademicPlanId(academicPlanId));
        countPriority(disciplineDTOS);
        return disciplineDTOS;
    }

    private void countPriority(List<DisciplineDTO> disciplineDTOS) {
        disciplineDTOS.forEach(d -> {
            d.setValue(d.getDisciplineResults().stream().mapToInt(DisciplineResultDTO::getPriority).sum());
        });
    }

    private List<DisciplineDTO> mapToDisciplineDTO(List<Discipline> disciplines) {
        List<DisciplineDTO> list = new ArrayList<>();
        disciplines.forEach(v -> {
            var elem = disciplineMapping.mapToDisciplineDto(v);
            list.add(elem);
        });
        return list;
    }

    public List<String> getAllKafs() {
        return disciplineRepository.findUnitNames();
    }

    public List<String> getAllFaks() {
        return planRepository.findDistinctFaculties();
    }

    public List<String> getAllPros() {
        return planRepository.findDistinctProfiles();
    }

    public List<DisciplineDTO> getDisciplinesByKafedra(String special) {
        List<DisciplineDTO> disciplineDTOS = disciplineRepository.findDisciplinesByAcademicPlanUnitNameContains(special)
                .stream()
                .map(disciplineMapping::mapToDisciplineDto)
                .toList();
        countPriority(disciplineDTOS);
        return disciplineDTOS;
    }

    public List<DisciplineDTO> getDisciplinesByFaculty(String special) {
        List<DisciplineDTO> disciplineDTOS =  disciplineRepository.findDisciplinesByPlan_academicPlanFacultyNameContains(special)
                .stream()
                .map(disciplineMapping::mapToDisciplineDto)
                .toList();
        countPriority(disciplineDTOS);
        return disciplineDTOS;
    }


    public List<DisciplineDTO> getDisciplinesByPofile(String special) {
        List<DisciplineDTO> disciplineDTOS =  disciplineRepository.findDisciplinesByPlan_academicPlanSpecialtyProfileContains(special)
                .stream()
                .map(disciplineMapping::mapToDisciplineDto)
                .toList();
        countPriority(disciplineDTOS);
        return disciplineDTOS;
    }

    public String createDocument(List<DisciplineDTO> nodisc,
                                 List<DisciplineDTO> normdisc,
                                 List<DisciplineDTO> okdisc,
                                 String mes) throws IOException {
        String name = new Date().getTime() + ".doc";
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        setRun(paragraph.createRun(), "Calibre LIght", 20, "000000",
                "Собранные данные", false, false);

        paragraph = document.createParagraph();
        setRun(paragraph.createRun(), "Calibre LIght", 16, "000000",
                mes, false, false);

        paragraph = document.createParagraph();
        setRun(paragraph.createRun(), "Calibre LIght", 16, "000000",
                "Дисциплины без привязанных материалов:", false, false);

        XWPFTable tableOne = document.createTable(nodisc.size() + 1, 4);
        XWPFTableRow tableOneRowOne = tableOne.getRow(0);
        tableOneRowOne.getCell(0).setText("id");
        tableOneRowOne.getCell(1).setText("Наименование");
        tableOneRowOne.getCell(2).setText("Формат");
        tableOneRowOne.getCell(3).setText("Приоритет");
        int index = 1;
        for (; index <= nodisc.size(); index++) {
            tableOneRowOne = tableOne.getRow(index);
            tableOneRowOne.getCell(0).setText(nodisc.get(index - 1).getId());
            tableOneRowOne.getCell(1).setText(nodisc.get(index - 1).getAcademicPlanDisciplineName());
            tableOneRowOne.getCell(2).setText(nodisc.get(index - 1).getAcademicPlanForm());
            tableOneRowOne.getCell(3).setText(String.valueOf(nodisc.get(index - 1).getValue()));
        }

        paragraph = document.createParagraph();
        setRun(paragraph.createRun(), "Calibre LIght", 16, "000000",
                "Дисциплины c привязанными материалами, но без оценки сопоставления:", false, false);

        tableOne = document.createTable(normdisc.size() + 1, 4);
        tableOneRowOne = tableOne.getRow(0);
        tableOneRowOne.getCell(0).setText("id");
        tableOneRowOne.getCell(1).setText("Наименование");
        tableOneRowOne.getCell(2).setText("Формат");
        tableOneRowOne.getCell(3).setText("Приоритет");
        index = 1;
        for (; index <= normdisc.size(); index++) {
            tableOneRowOne = tableOne.getRow(index);
            tableOneRowOne.getCell(0).setText(normdisc.get(index - 1).getId());
            tableOneRowOne.getCell(1).setText(normdisc.get(index - 1).getAcademicPlanDisciplineName());
            tableOneRowOne.getCell(2).setText(normdisc.get(index - 1).getAcademicPlanForm());
            tableOneRowOne.getCell(3).setText(String.valueOf(normdisc.get(index - 1).getValue()));
        }

        paragraph = document.createParagraph();
        setRun(paragraph.createRun(), "Calibre LIght", 16, "000000",
                "Дисциплины с заполненной обеспеченностью", false, false);

        tableOne = document.createTable(okdisc.size() + 1, 4);
        tableOneRowOne = tableOne.getRow(0);
        tableOneRowOne.getCell(0).setText("id");
        tableOneRowOne.getCell(1).setText("Наименование");
        tableOneRowOne.getCell(2).setText("Формат");
        tableOneRowOne.getCell(3).setText("Приоритет");
        index = 1;
        for (; index <= okdisc.size(); index++) {
            tableOneRowOne = tableOne.getRow(index);
            tableOneRowOne.getCell(0).setText(okdisc.get(index - 1).getId());
            tableOneRowOne.getCell(1).setText(okdisc.get(index - 1).getAcademicPlanDisciplineName());
            tableOneRowOne.getCell(2).setText(okdisc.get(index - 1).getAcademicPlanForm());
            tableOneRowOne.getCell(3).setText(String.valueOf(okdisc.get(index - 1).getValue()));
        }

        paragraph = document.createParagraph();
        setRun(paragraph.createRun(), "Calibre LIght", 20, "000000",
                "Незаполненных дисциплин: " + nodisc.size() + ",\n" +
                        "Частично заполненных: " + normdisc.size() + ",\n" +
                        "Заполненных: " + okdisc.size(), false, false);

        FileOutputStream outStream = new FileOutputStream(name);
        document.write(outStream);
        outStream.close();
        return name;
    }

    private static void setRun(XWPFRun run, String fontFamily, int fontSize, String colorRGB, String text, boolean bold, boolean addBreak) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setColor(colorRGB);
        run.setText(text);
        run.setBold(bold);
        if (addBreak) run.addBreak();
    }
}
