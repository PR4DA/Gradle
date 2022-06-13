package com.example.demo.config

import com.example.demo.service.UserService
import com.example.demo.utils.CustomAuthenticationSuccessHandler
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class DemoSecurityConfigTest extends Specification{

    def userService = Mock(UserService)

    def handler = Mock(CustomAuthenticationSuccessHandler)

    def config = new DemoSecurityConfig(userService, handler)

    def "When context gets loaded, auth provider gets created"() {
        when:
        def result = config.authenticationProvider()

        then:
        Objects.nonNull(result)
        result instanceof DaoAuthenticationProvider
    }

    def "When context gets loaded, BCryptPasswordEncoder gets created"() {
        when:
        def result = config.passwordEncoder()

        then:
        Objects.nonNull(result)
        result instanceof BCryptPasswordEncoder
    }





}
