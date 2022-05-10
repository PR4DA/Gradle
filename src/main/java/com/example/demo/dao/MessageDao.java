package com.example.demo.dao;

import com.example.demo.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDao extends JpaRepository<ChatMessage, Long> {

    //    List<ChatMessage> findAllAndOrderByTimestamp();
    List<ChatMessage> findAllBySenderOrderByTimestamp(String sender);

    List<ChatMessage> deleteAllBySender(String sender);

    List<ChatMessage> findChatMessagesByContent(String content);

}
