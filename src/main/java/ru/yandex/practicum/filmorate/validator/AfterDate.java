package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = AfterDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterDate {

  String message() default "Дата должна быть не раньше {targetDate}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String targetDate();
}
