package it.cnr.rethinkwaste.validation;

import javax.validation.*;
import java.lang.annotation.*;

@Repeatable(Conditionals.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConditionalValidator.class})
public @interface Conditional {

    String message() default "This field is required.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String selected();
    String[] required();
    String[] values();
}
