//package com.example.demo;
//
//import com.example.demo.dao.RoleDao;
//import com.example.demo.dao.UserDao;
//import com.example.demo.entity.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.annotation.Rollback;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
//public class UserDaoTests {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private UserDao userRepo;
//
//    @Autowired
//    private RoleDao roleRepo;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
////        @Test
////    public void testCreateUser() {
////        User user = new User();
////        user.setUserName("demoFirst");
////        user.setEmail("demo@gmail.com");
////        user.setPassword(passwordEncoder.encode("fun123"));
////        user.setFirstName("Demo");
////        user.setLastName("First");
////
////        User savedUser = userRepo.save(user);
////
////        User existUser = entityManager.find(User.class, savedUser.getId());
////
////        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
////
////    }
////
////    @Test
////    public void testAddRoleToNewUser() {
////
////        User user = new User();
////        user.setUserName("mikes");
////        user.setEmail("mikes.gates@gmail.com");
////        user.setPassword(passwordEncoder.encode("fun123"));
////        user.setFirstName("Mike");
////        user.setLastName("Gates");
////        user.addRole(roleRepo.findRoleByName("ROLE_ADMIN"));
////
////        User savedUser = userRepo.save(user);
////
////        assertThat(savedUser.getRoles().size()).isEqualTo(1);
////    }
////
////    @Test
////    public void testAddRoleToExistingUser() {
////        User user = userRepo.findById(1L).get();
////        Role roleUser = roleRepo.findRoleByName("ROLE_USER");
////        Role roleCustomer = new Role("3");
////
////        user.addRole(roleUser);
////        user.addRole(roleCustomer);
////
////        User savedUser = userRepo.save(user);
////
////        assertThat(savedUser.getRoles().size()).isEqualTo(2);
////    }
//
////        @Test
////    public void testAddMessageToUser() {
////        User user = userRepo.findById(1L).get();
////        ChatMessage chatMessage = new ChatMessage("Some message", user.getUserName(), JOIN);
////
////        user.addMessage(chatMessage);
////
////        User savedUser = userRepo.save(user);
////
////        assertThat(savedUser.getRoles().size()).isEqualTo(3);
////    }
//}