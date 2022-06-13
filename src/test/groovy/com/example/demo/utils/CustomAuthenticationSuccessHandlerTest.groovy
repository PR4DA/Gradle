package com.example.demo.utils

import com.example.demo.entity.User
import com.example.demo.service.UserService
import org.springframework.security.core.Authentication
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class CustomAuthenticationSuccessHandlerTest extends Specification{

    def userService = Mock(UserService)

    def customAuthenticationSuccessHandler = new CustomAuthenticationSuccessHandler(userService)

    def "Successful authentication response and parameters adding check"() {
        given:
        def request = Mock(HttpServletRequest)
        def response = Mock(HttpServletResponse)
        def authentication = Mock(Authentication)
        def session = Mock(HttpSession)

        when:
        customAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication)

        then:
        1 * authentication.getName() >> String
        1 * userService.findByUserName(_ as String) >> Mock(User)
        1 * request.getSession() >> session
        1 * session.setAttribute(_ as String, _ as User)


    }

}
