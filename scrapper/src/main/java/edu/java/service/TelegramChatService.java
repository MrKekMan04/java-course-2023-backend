package edu.java.service;

import edu.java.entity.TelegramChat;
import edu.java.entity.dto.DeleteChatResponse;
import edu.java.entity.dto.RegisterChatResponse;
import edu.java.exception.TelegramChatAlreadyRegistered;
import edu.java.exception.TelegramChatNotExistsException;
import edu.java.repository.TelegramChatRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class TelegramChatService {
    private final TelegramChatRepository chatRepository;

    public RegisterChatResponse registerChat(@PathVariable Long id) {
        chatRepository.findById(id).ifPresent(ignore -> {
            throw new TelegramChatAlreadyRegistered(id);
        });

        TelegramChat savedEntity = chatRepository.save(new TelegramChat()
            .setId(id)
            .setLinks(new ArrayList<>()));

        return new RegisterChatResponse(savedEntity != null);
    }

    public DeleteChatResponse deleteChat(@PathVariable Long id) {
        chatRepository.delete(chatRepository.findById(id)
            .orElseThrow(() -> new TelegramChatNotExistsException(id)));

        return new DeleteChatResponse(true);
    }
}
