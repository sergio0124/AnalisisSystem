package org.example.discipline;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DisciplineService {
    DisciplineRepository disciplineRepository;

    public void save(List<Discipline> disciplines){
        disciplineRepository.saveAll(disciplines);
    }
}
