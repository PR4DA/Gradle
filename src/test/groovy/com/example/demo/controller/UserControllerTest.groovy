package com.example.demo.controller

import com.example.demo.entity.User
import com.example.demo.service.UserService
import com.example.demo.user.CrmUser
import org.springframework.core.io.FileSystemResource
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

class UserControllerTest extends Specification {
    def userService = Mock(UserService)
    def userController = new UserController(userService)

    def "Adding to model users received from userService in method showHome"() {
        given:
        def theModel = Mock(Model)
        def users = new LinkedList()

        when:
        userController.showHome(theModel)

        then:
        1 * userService.findAll() >> users
        1 * theModel.addAttribute(_, _)
    }

    def "Adding new crmUser to model when method showMyLoginPage is called"() {
        given:
        def theModel = Mock(Model)

        when:
        userController.showMyLoginPage(theModel)

        then:
        1 * theModel.addAttribute(_ as String, _ as CrmUser) >> theModel
    }

    def "Adding to model user and his roles received from userService in method showFormForUpdate"() {
        given:
        def theModel = Mock(Model)
        def listRoles = new LinkedList()
        def user = Mock(User)

        when:
        userController.showFormForUpdate(1L, theModel)

        then:
        1 * userService.findById(_) >> user
        1 * userService.listRoles() >> listRoles
        2 * theModel.addAttribute(_, _) >> theModel
    }

    def "Returns file generated in user model in HttpServletResponse with added attributes"() {
        given:
        def response = Mock(HttpServletResponse)
        def user = Mock(User)
        def file = Mock(File)
        def id = 1L

        when:
        def result = userController.getFile(id, response)

        then:
        1 * userService.findById(id) >> user
        1 * user.getFileUrl() >> file
        1 * response.setContentType(_ as String)
        1 * response.setHeader(_, _)
        Objects.nonNull(result)
        result instanceof FileSystemResource
    }

    def "Calling method processRegistrationForm if user infoBinding has errors"() {
        given:
        def theCrmUser = Mock(CrmUser)
        def theBindingResult = Mock(BindingResult)
        def theModel = Mock(Model)

        when:
        userController.processRegistrationForm(theCrmUser, theBindingResult, theModel)

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
        userController.processRegistrationForm(theCrmUser, theBindingResult, theModel)

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
        def result = userController.processRegistrationForm(theCrmUser, theBindingResult, theModel)

        then:
        1 * userService.save(theCrmUser)
        result == "users/registration-confirmation"
    }

    def "If method delete is called userService.delete receive request"(){
        given:
        def id = 1L

        when:
        userController.delete(id)

        then:
        1 * userService.deleteById(id)
    }

    def "If method delete is called userService.delete receive request is caches error"(){
        given:
        def id = 1L
        def e = Mock(Error)

        when:
        userController.delete(id)

        then:
        1 * userService.deleteById(id) >> e.printStackTrace()
    }

}
