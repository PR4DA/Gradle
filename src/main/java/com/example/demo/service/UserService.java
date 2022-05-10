package com.example.demo.service;

import com.example.demo.entity.ChatMessage;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User findById(Long theId);

    User findByUserName(String userName);

    void save(CrmUser crmUser);

    void update(User user);

    void deleteById(Long theId);

    List<Role> listRoles();

    List<ChatMessage> listMessages();

    void saveUserMessage(String sender, ChatMessage chatMessage);
}
