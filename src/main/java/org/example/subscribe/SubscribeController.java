package org.example.subscribe;

import lombok.AllArgsConstructor;
import org.example.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class SubscribeController {
    SubscribeService subscribeService;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@AuthenticationPrincipal User user,
                                            @RequestBody Subscribe subscribe){
        subscribe.setUser(user);
        if (subscribeService.subscribe(subscribe)){
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("Данные о дисциплине не найдены");
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestBody Subscribe subscribe){
        if (subscribeService.unsubscribe(subscribe)){
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("Подписка не найдена");
        }
    }
}
