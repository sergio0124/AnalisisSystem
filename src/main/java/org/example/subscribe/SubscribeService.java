package org.example.subscribe;

import lombok.AllArgsConstructor;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineRepository;
import org.example.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscribeService {

    SubscribeRepository subscribeRepository;
    DisciplineRepository disciplineRepository;
    UserRepository userRepository;

    public boolean subscribe(Long id, String disciplineId) {
        Subscribe subscribe = new Subscribe();

        if (subscribeRepository.findSubscribeByUser_IdAndDiscipline_IdContains(id, disciplineId) != null) {
            return false;
        }
        Discipline discipline = null;

        if (disciplineRepository.findDisciplinesByIdContaining(disciplineId) != null) {
            discipline = disciplineRepository.findDisciplinesByIdContaining(disciplineId).get(0);
        }
        subscribe.setDiscipline(discipline);
        subscribe.setUser(userRepository.findUserById(id).orElse(null));
        if (discipline == null || subscribe.getUser() == null) {
            return false;
        }
        subscribeRepository.save(subscribe);
        return true;
    }

    public boolean unsubscribe(Long id, String disciplineId) {
        Subscribe subscribe = subscribeRepository.findSubscribeByUser_IdAndDiscipline_IdContains(id, disciplineId);
        if (subscribe == null) {
            return false;
        }
        subscribeRepository.delete(subscribe);
        return true;
    }


    public boolean isSubscribed(Long id, String disciplineId) {
        return (subscribeRepository.findSubscribeByUser_IdAndDiscipline_IdContains(id, disciplineId) != null);
    }
}
