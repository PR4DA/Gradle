package com.example.demo.validation

import org.springframework.beans.BeanWrapperImpl
import spock.lang.Specification

import javax.validation.ConstraintValidatorContext

class FieldMatchValidatorTest extends Specification {
    def fieldMatchValidator = new FieldMatchValidator()

    def "method initialize init methods test"(){
        given:
        def constraintAnnotation = Mock(FieldMatch)

        when:
        fieldMatchValidator.initialize(constraintAnnotation)

        then:
        1 * constraintAnnotation.first()
        1 * constraintAnnotation.second()
        1 * constraintAnnotation.message()
    }

    def "method isValid returns true with valid object received"() {
        given:
        def value = Mock(Object)
        def context = Mock(ConstraintValidatorContext)

        when:
        def result = fieldMatchValidator.isValid(value, context)

        then:
        result == true
    }

//    def "method isValid refreshes value with invalid object received"() {
//        given:
//        def value = Mock(Object)
//        def context = Mock(ConstraintValidatorContext)
//
//        when:
//        fieldMatchValidator.isValid(value, context) >> false
//
//        then:
//        context.buildConstraintViolationWithTemplate(_ as String) >> Mock(ConstraintValidatorContext.ConstraintViolationBuilder)
//    }
}
