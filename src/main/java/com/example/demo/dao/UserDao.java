package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserDao extends JpaRepository<User, Long> {

    List<User> findAll();

    User findByUserName(String userName);

    User save(User user);

    Optional<User> findById(Long id);

}