package com.bshukaylo.ai.local.service;

import com.bshukaylo.ai.local.model.Chat;
import com.bshukaylo.ai.local.model.Role;
import com.bshukaylo.ai.local.repository.ChatRepository;
import lombok.SneakyThrows;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatClient chatClient;
    private final ChatEntryService chatEntryService;

    @Autowired
    public ChatService(ChatRepository chatRepository,
                       ChatClient chatClient,
                       ChatEntryService chatEntryService) {
        this.chatRepository = chatRepository;
        this.chatClient = chatClient;
        this.chatEntryService = chatEntryService;
    }

    public List<Chat> getAllChats() {
        return chatRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Chat getChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow();
    }

    public Chat createNewChat(String title) {
        Chat chat = Chat.builder()
                .title(title)
                .build();
        return chatRepository.save(chat);
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    public SseEmitter proceedInteractionWithStreaming(Long chatId, String prompt) {
        chatEntryService.addChatEntry(chatId, prompt, Role.USER);

        StringBuilder answer = new StringBuilder();
        SseEmitter sseEmitter = new SseEmitter(0L);

        chatClient.prompt().user(prompt).stream()
                .chatResponse()
                .subscribe(
                        response -> processToken(response, answer, sseEmitter),
                        sseEmitter::completeWithError,
                        () -> chatEntryService.addChatEntry(chatId, answer.toString(), Role.ASSISTANT));

        return sseEmitter;
    }

    @SneakyThrows
    private static void processToken(ChatResponse response, StringBuilder answer, SseEmitter sseEmitter) {
        AssistantMessage result = response.getResult().getOutput();
        answer.append(result.getText());
        sseEmitter.send(result);
    }
}
