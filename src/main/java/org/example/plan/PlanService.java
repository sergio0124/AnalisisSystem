package org.example.plan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.example.discipline.DisciplineResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PlanService {
    String ATHENE_REPO = "http://plans.athene.tech/swagger-ui/index.html#/get-controller/getPlansUsingGET";
    PlanRepository planRepository;

    public void save(List<Plan> plans) {
        planRepository.saveAll(plans);
    }

    public void getPlanAndSave() throws URISyntaxException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        var json = getJson();
        var plans = getPlan(json);
        var results = getResults(json);
        save(plans);
    }

    private List<DisciplineResult> getResults(JsonObject json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<DisciplineResult> list = mapper
                .readValue(json.get("DisciplineResults")
                        .toString(), new TypeReference<>() {
                });
        return list;
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


    public List<Plan> getPlan(JsonObject json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<Plan> list = mapper
                .readValue(json.get("PlanList")
                        .toString(), new TypeReference<>() {
                });
        return list;
    }
}
