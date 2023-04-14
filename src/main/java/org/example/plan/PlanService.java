package org.example.plan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.example.discipline.DisciplineResult;
import org.example.discipline.DisciplineResultRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PlanService {
    final String ATHENE_REPO = "http://plans.athene.tech/swagger-ui/index.html#/get-controller/getPlansUsingGET";
    PlanRepository planRepository;
    DisciplineResultRepository disciplineResultRepository;

    private void savePlan(List<Plan> plans, List<DisciplineResult> disciplineResults) {
        Map<String, DisciplineResult> map = new HashMap<>();
        for (DisciplineResult dr : disciplineResults) {
            map.put(dr.getResultId(), dr);
        }
        plans.forEach(v -> {
            v.getDisciplinesOfPlan().forEach(d -> {
                d.setPlan(v);
                var list = d.getDisciplineResults();
                d.getDisciplineResultsList().forEach(rec->{
                    var res = map.get(rec);
                    list.add(res);
                    res.getDisciplines().add(d);
                });
            });
        });
        planRepository.saveAll(plans);
    }

    private void saveResults(List<DisciplineResult> disciplineResults){
        disciplineResultRepository.saveAll(disciplineResults);
    }

    public void getPlanAndSave() throws URISyntaxException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var json = getJson();

        var plans = getPlan(json);
        var results = getResults(json);

        savePlan(plans, results);
        saveResults(results);
    }

    private List<DisciplineResult> getResults(JsonObject json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper
                .readValue(json.get("DisciplineResults")
                        .toString(), new TypeReference<>() {
                });
    }

    private JsonObject getJson() throws URISyntaxException, IOException {
//        RestTemplate restTemplate = new RestTemplate();
//        int year = Calendar.YEAR;
//        String stringPosts = restTemplate.getForObject("http://plans.athene.tech/swagger-ui/index.html#/get-controller/getPlansUsingGET ", String.class);
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

    public List<Plan> getAllPlans() {
        return planRepository.findAllByOrderByAcademicPlanIdDesc();
    }
    public Plan getPlanById(String academicPlanId){
        return planRepository.findPlanByAcademicPlanId(academicPlanId);
    }
    @Transactional
    public void deletePlan(String academicPlanId) {
        planRepository.deletePlanByAcademicPlanId(academicPlanId);
    }
}
