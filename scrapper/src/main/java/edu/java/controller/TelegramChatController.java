package edu.java.controller;

import edu.java.entity.dto.DeleteChatResponse;
import edu.java.entity.dto.RegisterChatResponse;
import edu.java.service.TelegramChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class TelegramChatController {
    private final TelegramChatService chatService;

    @PostMapping("/{id}")
    public RegisterChatResponse registerChat(@PathVariable Long id) {
        return chatService.registerChat(id);
    }

    @DeleteMapping("/{id}")
    public DeleteChatResponse deleteChat(@PathVariable Long id) {
        return chatService.deleteChat(id);
    }
}
