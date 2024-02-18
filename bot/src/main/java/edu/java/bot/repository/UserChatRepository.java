package edu.java.bot.repository;

import edu.java.bot.entity.UserChat;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UserChatRepository {
    private final Map<Long, UserChat> repository = new HashMap<>();

    public void add(UserChat userChat) {
        repository.put(userChat.getChatId(), userChat);
    }

    public UserChat findById(Long id) {
        return repository.get(id);
    }
}
