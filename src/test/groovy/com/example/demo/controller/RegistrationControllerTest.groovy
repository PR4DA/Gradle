package com.example.demo.controller

import com.example.demo.entity.User
import com.example.demo.service.UserService
import com.example.demo.user.CrmUser
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import spock.lang.Specification

class RegistrationControllerTest extends Specification{

    def userService = Mock(UserService)
    def registrationController = new RegistrationController(userService)

    def "Get dataBinder to register custom stringTrimmerEditor"() {
        given:
        def dataBinder = Mock(WebDataBinder)

        when:
        registrationController.initBinder(dataBinder)

        then:
        1 * dataBinder.registerCustomEditor(_ , _)
    }

    def "Calling method showMyLoginPage method add attributes to model called"() {
        given:
        def theModel = Mock(Model)

        when:
        registrationController.showMyLoginPage(theModel)

        then:
        1 * theModel.addAttribute(_, _)
    }

    def "Calling method saveUser and sends update request to userService"() {
        given:
        def user = Mock(User)
        def theBindingResult = Mock(BindingResult)

        when:
        def result = registrationController.saveUser(user, theBindingResult)

        then:
        1 * userService.update(_)
        result.equalsIgnoreCase("redirect:/users/list")
    }

    def "Calling method saveUser and redirects to registration page if user infoBinding has errors"() {
        given:
        def user = Mock(User)
        def theBindingResult = Mock(BindingResult)

        when:
        registrationController.saveUser(user, theBindingResult)

        then:
        1 * theBindingResult.hasErrors() >> "users/register"
    }

    def "Calling method processRegistrationForm if user infoBinding has errors"() {
        given:
        def theCrmUser = Mock(CrmUser)
        def theBindingResult = Mock(BindingResult)
        def theModel = Mock(Model)

        when:
        registrationController.processRegistrationForm(theCrmUser, theBindingResult, theModel)

        then:
        1 * theBindingResult.hasErrors() >> "users/register"
    }

    def "Calling method processRegistrationForm and doesnt register"() {
        given:
        def theCrmUser = Mock(CrmUser)
        def theBindingResult = Mock(BindingResult)
        def theModel = Mock(Model)
        def user = Mock(User)

        and:
        userService.findByUserName(_) >> user

        when:
        registrationController.processRegistrationForm(theCrmUser, theBindingResult, theModel)

        then:
        1 * theModel.addAttribute(_ as String, _ as CrmUser)
        1 * theModel.addAttribute(_ as String, _ as String)
    }

    def "Calling method processRegistrationForm and sends save request to userService"() {
        setup:
        def theCrmUser = Mock(CrmUser)
        def theBindingResult = Mock(BindingResult)
        def theModel = Mock(Model)

        and:
        theBindingResult.hasErrors() >> false
        userService.findByUserName(_ as String) >> null

        when:
        def result = registrationController.processRegistrationForm(theCrmUser, theBindingResult, theModel)

        then:
        1 * userService.save(theCrmUser)
        result == "users/registration-confirmation"
    }
}
