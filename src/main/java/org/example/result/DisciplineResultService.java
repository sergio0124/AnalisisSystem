package org.example.result;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DisciplineResultService {
    DisciplineResultRepository disciplineResultRepository;

    public void save(List<DisciplineResult> disciplineResults){
        disciplineResultRepository.saveAll(disciplineResults);
    }
}
