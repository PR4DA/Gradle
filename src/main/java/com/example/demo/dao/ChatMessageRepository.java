package com.example.demo.dao;

import com.example.demo.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<Collection<ChatMessage>> getChatMessagesBySender(String sender);
}
