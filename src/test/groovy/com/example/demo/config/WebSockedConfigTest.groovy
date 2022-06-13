package com.example.demo.config

import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration
import spock.lang.Specification

class WebSockedConfigTest extends Specification {

    def webSockedConfig = new WebSockedConfig()

    def "registerStompEndpoints call executes addEndpoint method once"() {
        setup:
        def registry = Mock(StompEndpointRegistry)
        def registration = Mock(StompWebSocketEndpointRegistration)

        when:
        webSockedConfig.registerStompEndpoints(registry)

        then:
        1 * registry.addEndpoint(_ as String) >> registration
    }

    def "configure message broker call enables broker and sets prefixes"() {
        given:
        def registry = Mock(MessageBrokerRegistry)

        when:
        webSockedConfig.configureMessageBroker(registry)

        then:
        1 * registry.enableSimpleBroker(_ as String)
        1 * registry.setApplicationDestinationPrefixes(_ as String)
    }
}
