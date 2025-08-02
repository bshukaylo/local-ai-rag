package com.bshukaylo.ai.local.configuration;

import com.bshukaylo.ai.local.repository.ChatRepository;
import com.bshukaylo.ai.local.service.memory.PostgresChatMemory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

    @Bean
    public ChatMemory chatMemory(ChatRepository chatRepository) {
        return PostgresChatMemory.builder()
                .maxMessages(2)
                .chatMemoryRepository(chatRepository)
                .build();
    }

    @Bean
    public MessageChatMemoryAdvisor messageChatMemoryAdvisor(ChatMemory chatMemory) {
        return MessageChatMemoryAdvisor.builder(chatMemory).build();
    }

    @Bean
    public ChatClient chatClient(MessageChatMemoryAdvisor messageChatMemoryAdvisor, ChatClient.Builder builder) {
        return builder
                .defaultAdvisors(messageChatMemoryAdvisor)
                .build();
    }

}
