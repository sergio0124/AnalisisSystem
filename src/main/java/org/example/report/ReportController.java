package org.example.report;

import lombok.AllArgsConstructor;
import org.example.discipline.DisciplineDTO;
import org.example.plan.Plan;
import org.example.plan.PlanDTO;
import org.example.plan.PlanService;
import org.example.user.UserMapping;
import org.example.user.User;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ReportController {
    UserMapping userMapping;
    ReportService reportService;
    PlanService planService;
    private ServletContext servletContext;

    @PostMapping("/report/get_plans")
    public ResponseEntity<Object> getAllPlans() {
        List<Kort> plans = planService.getAllPlans().stream()
                .map(val -> new Kort(val.getAcademicPlanId(),
                        val.getAcademicPlanQualificationName() + ", " +
                                val.getAcademicPlanSpecialtyProfile() + ", " +
                                val.getAcademicPlanStudyForm() + ", " +
                                val.getAcademicPlanComment()))
                .toList();
        return ResponseEntity.ok(plans);
    }

    @PostMapping("/report/get_kafs")
    public ResponseEntity<Object> getAllKafs() {
        List<Kort> kafs = reportService.getAllKafs()
                .stream()
                .map(v -> new Kort(v.replaceAll("[^A-Za-zа-яА-Я\s]", "---"), v))
                .toList();
        return ResponseEntity.ok(kafs);
    }

    @PostMapping("/report/get_pros")
    public ResponseEntity<Object> getAllPros() {
        List<Kort> pros = reportService.getAllPros()
                .stream()
                .map(v -> new Kort(v, v))
                .toList();
        return ResponseEntity.ok(pros);
    }

    @PostMapping("/report/get_faks")
    public ResponseEntity<Object> getAllFaks() {
        List<Kort> faks = reportService.getAllFaks()
                .stream()
                .map(v -> new Kort(v, v))
                .toList();
        ;
        return ResponseEntity.ok(faks);
    }

    @GetMapping("/report/")
    public String getChoosePage(@AuthenticationPrincipal User user,
                                Map<String, Object> model) {
        List<PlanDTO> plans = planService.getAllPlans();
        model.put("user", user);
        model.put("plans", plans);
        return "report_choose";
    }

    @GetMapping("/report/create")
    public String getDisciplineReport(@AuthenticationPrincipal User user,
                                      @RequestParam(required = false) String mode,
                                      @RequestParam(required = false) String special,
                                      Map<String, Object> model) {
        long time = System.currentTimeMillis();
        List<DisciplineDTO> nodisc = null;
        List<DisciplineDTO> normdisc = null;
        List<DisciplineDTO> okdisc = null;
        List<DisciplineDTO> disciplineDTOList = null;
        String mes = "";

        if (mode.equals("plan")) {
            Plan plan = planService.getPlanById(special);
            mes = "Отчет по плану: " +
                    "id: " + plan.getAcademicPlanId() + ", " +
                    "Степень: " + plan.getAcademicPlanQualificationName() + ", " +
                    "Профиль: " + plan.getAcademicPlanSpecialtyProfile() + ", " +
                    "Форма обучения: " + plan.getAcademicPlanStudyForm() + ", " +
                    "Комментарий: " + plan.getAcademicPlanComment();
            model.put("report_message", mes);
            disciplineDTOList = reportService.getDisciplinesByPlanId(special);
        } else if (mode.equals("kaf")) {
            special = special.replaceAll("---", "\"");
            mes = "Отчет по дисциплинам в разрезе кафедры "
                    + special;
            model.put("report_message", mes);
            disciplineDTOList = reportService.getDisciplinesByKafedra(special);
        } else if (mode.equals("fak")) {
            mes = "Отчет по дисциплинам в разрезе факультета " + special;
            model.put("report_message", mes);
            disciplineDTOList = reportService.getDisciplinesByFaculty(special);
        } else if (mode.equals("pro")) {
            mes = "Отчет по дисциплинам в разрезе профиля " + special;
            model.put("report_message", mes);
            disciplineDTOList = reportService.getDisciplinesByPofile(special);
        }

        nodisc = getDisciplinesWithNoBooks(disciplineDTOList);
        normdisc = getDisciplinesWithNoComparison(disciplineDTOList);
        okdisc = getDisciplinesWithComparison(disciplineDTOList);

        nodisc = sortDisciplinesByPriority(nodisc);
        normdisc = sortDisciplinesByPriority(normdisc);
        okdisc = sortDisciplinesByPriority(okdisc);

        model.put("user", user);
        model.put("nodisc", nodisc);
        model.put("normdisc", normdisc);
        model.put("okdisc", okdisc);
        float minutes = (float) (System.currentTimeMillis() - time) / (1000 * 60);
        model.put("time", minutes);

        return "report_page";
    }

    @GetMapping("/report/create/word")
    public ResponseEntity<Object> loadFile(@RequestParam(required = false) String mode,
                                           @RequestParam(required = false) String special) throws IOException {
        List<DisciplineDTO> nodisc = null;
        List<DisciplineDTO> normdisc = null;
        List<DisciplineDTO> okdisc = null;
        List<DisciplineDTO> disciplineDTOList = null;
        String mes = "";

        if (mode.equals("plan")) {
            Plan plan = planService.getPlanById(special);
            mes = "Отчет по плану: " +
                    "id: " + plan.getAcademicPlanId() + ", " +
                    "Степень: " + plan.getAcademicPlanQualificationName() + ", " +
                    "Профиль: " + plan.getAcademicPlanSpecialtyProfile() + ", " +
                    "Форма обучения: " + plan.getAcademicPlanStudyForm() + ", " +
                    "Комментарий: " + plan.getAcademicPlanComment();
            disciplineDTOList = reportService.getDisciplinesByPlanId(special);
        } else if (mode.equals("kaf")) {
            special = special.replaceAll("---", "\"");
            mes = "Отчет по дисциплинам в разрезе кафедры "
                    + special;
            disciplineDTOList = reportService.getDisciplinesByKafedra(special);
        } else if (mode.equals("fak")) {
            mes = "Отчет по дисциплинам в разрезе факультета " + special;
            disciplineDTOList = reportService.getDisciplinesByFaculty(special);
        } else if (mode.equals("pro")) {
            mes = "Отчет по дисциплинам в разрезе профиля " + special;
            disciplineDTOList = reportService.getDisciplinesByPofile(special);
        }

        nodisc = getDisciplinesWithNoBooks(disciplineDTOList);
        normdisc = getDisciplinesWithNoComparison(disciplineDTOList);
        okdisc = getDisciplinesWithComparison(disciplineDTOList);

        nodisc = sortDisciplinesByPriority(nodisc);
        normdisc = sortDisciplinesByPriority(normdisc);
        okdisc = sortDisciplinesByPriority(okdisc);
        String documentName = reportService.createDocument(
                nodisc,
                normdisc,
                okdisc,
                mes
        );

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, documentName);

        File file = new File(documentName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    private List<DisciplineDTO> getDisciplinesWithNoBooks(List<DisciplineDTO> disciplineDTOS) {
        return disciplineDTOS.stream()
                .filter(v -> v.getComparisons() == null || v.getComparisons().size() == 0)
                .toList();
    }

    private List<DisciplineDTO> getDisciplinesWithNoComparison(List<DisciplineDTO> disciplineDTOS) {
        return disciplineDTOS.stream()
                .filter(v -> v.getComparisons().stream().noneMatch(c -> c.getMark() > 0)
                        && v.getComparisons().size() > 0)
                .toList();
    }

    private List<DisciplineDTO> getDisciplinesWithComparison(List<DisciplineDTO> disciplineDTOS) {
        return disciplineDTOS.stream()
                .filter(v -> v.getComparisons().stream().anyMatch(c -> c.getMark() > 0))
                .toList();
    }

    private List<DisciplineDTO> sortDisciplinesByPriority(List<DisciplineDTO> disciplineDTOS) {
        return disciplineDTOS.stream().sorted(Comparator.comparing(DisciplineDTO::getValue, Comparator.reverseOrder())).toList();
    }
}
