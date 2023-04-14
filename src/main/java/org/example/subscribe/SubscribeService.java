package org.example.subscribe;

import lombok.AllArgsConstructor;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscribeService {

    SubscribeRepository subscribeRepository;
    DisciplineRepository disciplineRepository;

    public boolean subscribe(Subscribe subscribe){
        Discipline discipline = disciplineRepository.findById(subscribe.getDiscipline().getId()).orElse(null);
        subscribe.setDiscipline(discipline);
        if(discipline==null){
            return false;
        }
        subscribeRepository.save(subscribe);
        return true;
    }

    public boolean unsubscribe(Subscribe subscribe){
        var s = subscribeRepository.findById(subscribe.getId()).orElse(null);
        if(s==null){
            return false;
        }
        subscribeRepository.delete(s);
        return true;
    }
}
