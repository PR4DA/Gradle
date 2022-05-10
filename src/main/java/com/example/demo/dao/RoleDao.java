package com.example.demo.dao;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {

    Role findRoleByName(String theRoleName);

}
