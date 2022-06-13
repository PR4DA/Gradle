package com.example.demo.service

import com.example.demo.dao.MessageDao
import com.example.demo.dao.RoleDao
import com.example.demo.dao.UserDao
import com.example.demo.entity.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class UserServiceImplTest extends Specification {

    def userDao = Mock(UserDao)
    def roleDao = Mock(RoleDao)
    def messageDao = Mock(MessageDao)
    def passwordEncoder = Mock(BCryptPasswordEncoder)

    def service = new UserServiceImpl(userDao, roleDao, passwordEncoder, messageDao)


    def "Check if service gets initialize"() {
        when:
        def service = new UserServiceImpl(userDao, roleDao, passwordEncoder, messageDao)

        then:
        service != null
        service instanceof UserServiceImpl
    }

    def "If given user id is correct user gets returned"() {
        setup:
        def user = Mock(User)

        and:
        userDao.findById(_ as Long) >> Optional.of(user)

        when:
        def result = service.findById(1)

        then:
        Objects.nonNull(result)
        result instanceof User
    }

    def "If given user id is incorrect user does not get returned"() {
        setup:
        def optionalUser = Optional.empty()

        and:
        userDao.findById(_ as Long) >> optionalUser

        when:
        def result = service.findById(1)

        then:
        Objects.isNull(result)
    }


}
