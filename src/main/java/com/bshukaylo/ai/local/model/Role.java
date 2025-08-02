package com.bshukaylo.ai.local.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Role {
    USER("user") {
        @Override
        public Message getMessage(String prompt) {
            return new UserMessage(prompt);
        }
    },
    ASSISTANT("assistant") {
        @Override
        public Message getMessage(String prompt) {
            return new AssistantMessage(prompt);
        }
    },
    SYSTEM("system") {
        @Override
        public Message getMessage(String prompt) {
            return new SystemMessage(prompt);
        }
    };

    private final String role;

    public static Role fromString(String roleName) {
        return Arrays.stream(Role.values())
                .filter(it -> it.role.equals(roleName))
                .findFirst()
                .orElseThrow();
    }

    public abstract Message getMessage(String prompt);
}
