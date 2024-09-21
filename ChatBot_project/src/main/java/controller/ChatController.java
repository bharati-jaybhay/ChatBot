package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    @GetMapping("/chat")
    public ResponseEntity<String> getChatResponse() {
        return ResponseEntity.ok("Chat response");
    }
}
