package org.example.plan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.example.discipline.Discipline;
import org.example.result.DisciplineResult;
import org.example.result.DisciplineResultRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PlanService {
    final String ATHENE_REPO = "http://plans.athene.tech:80/get-plans?year-start=----&year-end=____";
    PlanRepository planRepository;
    DisciplineResultRepository disciplineResultRepository;
    PlanMapping planMapping;

    private void savePlan(List<Plan> plans, List<DisciplineResult> disciplineResults) {
        Map<String, DisciplineResult> map = new HashMap<>();
        for (DisciplineResult dr : disciplineResults) {
            map.put(dr.getId(), dr);
        }
        plans.forEach(v -> {
            v.getDisciplinesOfPlan().forEach(d -> {
                d.setPlan(v);
                var list = d.getDisciplineResults();
                d.getDisciplineResultsList().forEach(rec -> {
                    var res = map.get(rec);
                    list.add(res);
                    res.getDisciplines().add(d);
                });
            });
        });
        planRepository.saveAll(plans);
    }

    private void saveResults(List<DisciplineResult> disciplineResults) {
        disciplineResultRepository.saveAll(disciplineResults);
    }

    public void getPlanAndSave(int start, int end) throws URISyntaxException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var json = getJson(start, end);

        var plans = getPlan(json);
        var results = getResults(json);
        uniteDisciplines(plans);

        savePlan(plans, results);
        saveResults(results);
    }

    private void uniteDisciplines(List<Plan> plans) {
        plans.forEach(plan -> {
            var iterator = plan.getDisciplinesOfPlan().iterator();
            Discipline disciplineUnion = null;
            while (iterator.hasNext()){
                var discipline = iterator.next();
                if (disciplineUnion == null ||
                        !Objects.equals(
                                discipline.getAcademicPlanDisciplineId(),
                                disciplineUnion.getAcademicPlanDisciplineId()
                        ) ||
                        !Objects.equals(
                                disciplineUnion.getAcademicPlanSemesterNumber(),
                                discipline.getAcademicPlanSemesterNumber()
                        )){
                    discipline.setId(
                            discipline.getAcademicPlanDisciplineId() +
                            discipline.getAcademicPlanSemesterNumber() +
                            discipline.getAcademicPlanId()
                    );
                    disciplineUnion = discipline;
                } else {
                    disciplineUnion.setAcademicPlanForm(disciplineUnion.getAcademicPlanForm() + ", " + discipline.getAcademicPlanForm());
                    disciplineUnion.setAcademicPlanAmountByPlan((int) (zeroIfNull(disciplineUnion.getAcademicPlanAmountByPlan()) + zeroIfNull(discipline.getAcademicPlanAmountByPlan())));
                    if (!"".equals(discipline.getAcademicPlanUnitFormRule())) {
                        disciplineUnion.setAcademicPlanUnitFormRule(disciplineUnion.getAcademicPlanUnitFormRule() + ", " + discipline.getAcademicPlanUnitFormRule());
                    }
                    disciplineUnion.setAcademicPlanUnitAmountByPlan((int) (zeroIfNull(disciplineUnion.getAcademicPlanUnitAmountByPlan()) + zeroIfNull(discipline.getAcademicPlanUnitAmountByPlan())));
                    disciplineUnion.setAcademicPlanDisciplineFormZET(zeroIfNull(disciplineUnion.getAcademicPlanDisciplineFormZET()) + zeroIfNull(discipline.getAcademicPlanDisciplineFormZET()));
                    iterator.remove();
                }
            }
        });
    }

    private float zeroIfNull(Number number) {
        return (number == null) ? 0 : number.floatValue();
    }

    public List<PlanDTO> getAllPlans() {
        return planRepository.findAll(Sort.by(Sort.Direction.ASC, "academicPlanSpecialtyProfile")).stream().map(planMapping::mapToPlanDTO).toList();
    }

    public List<PlanDTO> getAllPlansByPage(Pageable pageable){
        return planRepository.findAll(pageable).getContent().stream().map(planMapping::mapToPlanDTO).toList();
    }

    public Plan getPlanById(String academicPlanId) {
        return planRepository.findPlanByAcademicPlanId(academicPlanId);
    }

    public List<PlanDTO> getPlansByAcademicName(String name, Pageable pageable){
        return planRepository.findPlansByAcademicPlanSpecialtyProfileContainingIgnoreCase(name, pageable)
                .getContent()
                .stream()
                .map(planMapping::mapToPlanDTO)
                .toList();
    }

    @Transactional
    public void deletePlan(String academicPlanId) {
        planRepository.deletePlanByAcademicPlanId(academicPlanId);
    }


    private List<DisciplineResult> getResults(JsonObject json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper
                .readValue(json.get("DisciplineResults")
                        .toString(), new TypeReference<>() {
                });
    }

    private JsonObject getJson(int start, int end) throws URISyntaxException, IOException {
//        RestTemplate restTemplate = new RestTemplate();
//        String stringPosts = restTemplate
//                .getForObject(ATHENE_REPO.replaceAll("----", String.valueOf(start)).replaceAll("____", String.valueOf(end)), String.class);
//        assert stringPosts != null;
//        return JsonParser.parseString(stringPosts)
//                .getAsJsonObject()
//                .get("structureOfAcademicPlans")
//                .getAsJsonObject();

        JsonParser jp = new JsonParser();
        URL url = this.getClass()
                .getClassLoader()
                .getResource("resp.json");
        Path path = Paths.get(url.toURI());

        StringBuilder result = new StringBuilder(Files.readAllLines(path, StandardCharsets.UTF_8).stream()
                .collect(Collectors.joining("", "", "")));
        return jp.parse(result.toString())
                .getAsJsonObject()
                .get("structureOfAcademicPlans")
                .getAsJsonObject();
    }


    private List<Plan> getPlan(JsonObject json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<Plan> list = mapper
                .readValue(json.get("PlanList")
                        .toString(), new TypeReference<>() {
                });
        return list;
    }
}
