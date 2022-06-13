package com.example.demo.controller

import com.example.demo.entity.ChatMessage
import com.example.demo.service.UserService
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import spock.lang.Specification

class ChatControllerTest extends Specification{
    def service = Mock(UserService)
    def controller = new ChatController(service)

    def "Expect chat message response with right parameters"() {
        setup:
        def message = Mock(ChatMessage)
        def handler = Mock(SimpMessageHeaderAccessor)

        when:
        def result = controller.register(message, handler)

        then:
        Objects.nonNull(result)
        result instanceof ChatMessage
        1 * handler.getSessionAttributes() >> new HashMap<>()
    }

    def "Null is returned when register method used wrong values"() {
        setup:
        def message = null
        def handler = Mock(SimpMessageHeaderAccessor)

        when:
        def result = controller.register(message, handler)

        then:
        Objects.isNull(result)
    }

    def "Expect chat message in sendMessage method will be saved in the db" () {
        given:
        def message = Mock(ChatMessage)

        when:
        def result = controller.sendMessage(message)

        then:
        1 * service.saveUserMessage(_, _)
        Objects.nonNull(result)
        result instanceof ChatMessage
    }
}
