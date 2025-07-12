package com.bshukaylo.ai.local.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Role {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system");

    private final String role;

    public static Role fromString(String roleName) {
        return Arrays.stream(Role.values())
                .filter(it -> it.role.equals(roleName))
                .findFirst()
                .orElseThrow();
    }
}
