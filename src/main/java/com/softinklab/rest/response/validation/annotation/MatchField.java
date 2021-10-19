package com.softinklab.rest.response.validation.annotation;

import com.softinklab.rest.response.validation.validator.MatchFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MatchFieldValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchField {
    String message() default "Fields are not matching";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String match();

    String with();
}
