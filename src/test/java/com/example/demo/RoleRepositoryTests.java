//package com.example.demo;
//
//import com.example.demo.dao.RoleDao;
//import com.example.demo.entity.Role;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.Collection;
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
//public class RoleRepositoryTests {
//
//    @Autowired
//    private RoleDao repo;
//
////    @Test
////    public void testCreateRoles() {
////        Role user = new Role("ROLE_USER");
////        Role admin = new Role("ROLE_ADMIN");
////
////        List<Role> roles = new LinkedList<>();
////        roles.add(user);
////        roles.add(admin);
////
////        repo.saveAll(roles);
////
////        Collection<Role> listRoles = repo.findAll();
////
////        assertThat(listRoles.size()).isEqualTo(2);
////    }
//
//}
