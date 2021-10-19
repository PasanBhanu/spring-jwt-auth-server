package com.softinklab.rest.response.validation.validator;

import com.softinklab.rest.response.validation.annotation.MatchField;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchFieldValidator implements ConstraintValidator<MatchField, Object> {
    private String match;
    private String with;
    private String message;

    @Override
    public void initialize(MatchField constraintAnnotation) {
        this.match = constraintAnnotation.match();
        this.with = constraintAnnotation.with();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        PropertyAccessor bean = new BeanWrapperImpl(value);
        String matchValue = bean.getPropertyValue(this.match).toString();
        String matchWithValue = bean.getPropertyValue(this.with).toString();
        if (matchValue.equals(matchWithValue)) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(this.match)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
