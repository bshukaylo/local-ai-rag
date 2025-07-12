package com.bshukaylo.ai.local.controller;

import com.bshukaylo.ai.local.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер отвечает за базовые функции фронта:
 * <ul>
 *     <li>отображение главной страницы</li>
 *     <li>создание чата</li>
 *     <li>отображение чата</li>
 *     <li>удаление чата</li>
 * </ul>
 *
 * За общение с моделью отвечает {@link StreamingChatController}
 */
@Controller
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/")
    public String mainPage(ModelMap model) {
        model.addAttribute("chats", chatService.getAllChats());
        return "chat";
    }

    @GetMapping("/chat/{chatId}")
    public String showChat(ModelMap model, @PathVariable("chatId") Long chatId) {
        model.addAttribute("chats", chatService.getAllChats());
        model.addAttribute("chat", chatService.getChat(chatId));
        return "chat";
    }

    @PostMapping("/chat/new")
    public String newChat(@RequestParam String title) {
        var chat = chatService.createNewChat(title);
        return "redirect:/chat/" + chat.getId();
    }

    @PostMapping("/chat/{chatId}/delete")
    public String deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return "redirect:/";
    }
}
