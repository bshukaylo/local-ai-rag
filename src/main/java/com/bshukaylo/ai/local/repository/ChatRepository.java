package com.bshukaylo.ai.local.repository;

import com.bshukaylo.ai.local.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
