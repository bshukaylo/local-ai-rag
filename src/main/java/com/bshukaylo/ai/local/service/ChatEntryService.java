package com.bshukaylo.ai.local.service;

import com.bshukaylo.ai.local.model.Chat;
import com.bshukaylo.ai.local.model.ChatEntry;
import com.bshukaylo.ai.local.model.Role;
import com.bshukaylo.ai.local.repository.ChatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatEntryService {
    private final ChatRepository chatRepository;

    @Autowired
    public ChatEntryService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Transactional
    public void addChatEntry(Long chatId, String prompt, Role role) {
        Chat chat = chatRepository.findById(chatId).orElseThrow();
        ChatEntry entry = ChatEntry.builder().content(prompt).role(role).build();
        chat.addEntry(entry);
    }
}
