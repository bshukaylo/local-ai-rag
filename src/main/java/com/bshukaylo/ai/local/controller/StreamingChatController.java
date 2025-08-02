package com.bshukaylo.ai.local.controller;

import com.bshukaylo.ai.local.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * Контроллер отвечает за общение с моделью и возвратом ее ответов в стриминге
 */
@RestController
public class StreamingChatController {
    private final ChatService chatService;

    @Autowired
    public StreamingChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(value = "/chat-stream/{chatId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter talkToModel(@PathVariable Long chatId,
                                  @RequestParam String userPrompt) {
        return chatService.proceedInteractionWithStreaming(chatId, userPrompt);
    }
}
