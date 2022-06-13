package com.example.demo.validation

import spock.lang.Specification

import javax.validation.ConstraintValidatorContext
import java.util.regex.Matcher
import java.util.regex.Pattern

class EmailValidatorTest extends Specification{
    def emailValidator = new EmailValidator()

    def "isValid method with valid email should view true"() {
        given:
        def context = Mock(ConstraintValidatorContext)
        def email = "mineemail@gmail.com"

        when:
        def result = emailValidator.isValid(email, context)

        then:
        result == true
    }

    def "isValid method with invalid email should view false"() {
        given:
        def context = Mock(ConstraintValidatorContext)
        def email = "invalid"

        when:
        def result = emailValidator.isValid(email, context)

        then:
        result == false
    }
}
