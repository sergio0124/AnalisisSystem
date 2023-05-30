package org.example.subscribe;

import lombok.AllArgsConstructor;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineDTO;
import org.example.event.Event;
import org.example.event.EventDTO;
import org.example.event.EventService;
import org.example.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class SubscribeController {
    SubscribeService subscribeService;
    EventService eventService;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@AuthenticationPrincipal User user,
                                            @RequestParam String disciplineId){
        if (subscribeService.subscribe(user.getId(), disciplineId)){
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("Данные о дисциплине не найдены");
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam String disciplineId,
                                              @AuthenticationPrincipal User user){
        if (subscribeService.unsubscribe(user.getId(), disciplineId)){
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("Подписка не найдена");
        }
    }

    @GetMapping("/subscribes/")
    public String getSubscribeEventsPage(
            @AuthenticationPrincipal User user,
            Map<String, Object> model
    ){
        List<DisciplineDTO> disciplinesForUser = eventService.getDisciplinesForUser(user.getId());
        model.put("user", user);
        model.put("disciplines", disciplinesForUser);
        return "disc_subscribes";
    }


    @GetMapping("/subscribes/events")
    public String getSubscribePage(
            @AuthenticationPrincipal User user,
            Map<String, Object> model
    ){
        List<EventDTO> eventDTOS = eventService.getEventsForUser(user.getId());
        model.put("user", user);
        model.put("events", eventDTOS);
        return "subscribes";
    }
}
