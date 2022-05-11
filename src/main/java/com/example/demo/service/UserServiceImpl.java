package com.example.demo.service;

import com.example.demo.dao.MessageDao;
import com.example.demo.dao.RoleDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.ChatMessage;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    // need to inject user dao
    @Autowired
    private final UserDao userDao;

    @Autowired
    private final RoleDao roleDao;

    @Autowired
    private final MessageDao messageDao;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder, MessageDao messageDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.messageDao = messageDao;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public User findByUserName(String userName) {
        // check the database if the user already exists
        return userDao.findByUserName(userName);
    }

    @Override
    @Transactional
    public User findById(Long theId) {
        return userDao.findById(theId).get();
    }

    @Override
    @Transactional
    public void update(User temp) {
        User user = userDao.getById(temp.getId());
        if (!temp.getUserName().isEmpty()) {
            user.setUserName(temp.getUserName());
            List<ChatMessage> messages = user.getMessages();
            for (ChatMessage message : messages) {
                message.setSender(temp.getUserName());
            }
        }
        if (!temp.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(temp.getPassword()));
        }
        if (!temp.getFirstName().isEmpty()) {
            user.setFirstName(temp.getFirstName());
        }
        if (!temp.getLastName().isEmpty()) {
            user.setLastName(temp.getLastName());
        }
        if (!temp.getEmail().isEmpty()) {
            user.setEmail(temp.getEmail());
        }
        if (temp.getRoles().size() != 0) {
            user.setRoles(temp.getRoles());
        }
        userDao.save(user);
    }

    @Override
    @Transactional
    public void save(CrmUser crmUser) {
        User user = new User();
        // assign user details to the user object
        user.setUserName(crmUser.getUserName());
        user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
        user.setFirstName(crmUser.getFirstName());
        user.setLastName(crmUser.getLastName());
        user.setEmail(crmUser.getEmail());

        // give user default role of "employee"
        user.setRoles(Arrays.asList(roleDao.findRoleByName("3")));

        // save user in the database
        userDao.save(user);
    }

    @Override
    @Transactional
    public void saveUserMessage(String sender, ChatMessage chatMessage) {
        User user = userDao.findByUserName(sender);
        user.addMessage(chatMessage);
        // save user in the database
        userDao.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long theId) {
        try {
            User user = userDao.getById(theId);
            user.setRoles(null);
            user.setMessages(null);
            messageDao.deleteAllBySender(user.getUserName());
            userDao.save(user);
            userDao.delete(user);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public List<Role> listRoles() {
        return roleDao.findAll();
    }

    @Override
    public List<ChatMessage> listMessages() {
//        return messageDao.findAllAndOrderByTimestampAsc();
        return messageDao.findAll();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}