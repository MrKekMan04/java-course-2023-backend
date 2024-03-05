package edu.java.repository;

import edu.java.entity.TelegramChat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class TelegramChatRepositoryImpl implements TelegramChatRepository {
    private final Map<Long, TelegramChat> repository = new HashMap<>();

    @Override
    public Optional<TelegramChat> findById(Long id) {
        return repository.containsKey(id)
            ? Optional.of(repository.get(id))
            : Optional.empty();
    }

    @Override
    public TelegramChat save(TelegramChat telegramChat) {
        repository.put(telegramChat.getId(), telegramChat);

        return telegramChat;
    }

    @Override
    public void delete(TelegramChat telegramChat) {
        repository.remove(telegramChat.getId());
    }
}
