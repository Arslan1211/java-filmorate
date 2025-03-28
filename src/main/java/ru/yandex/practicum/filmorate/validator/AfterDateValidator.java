package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AfterDateValidator implements ConstraintValidator<AfterDate, LocalDate> {

  private LocalDate targetDate;

  @Override
  public void initialize(AfterDate constraintAnnotation) {
    this.targetDate = LocalDate.parse(
        constraintAnnotation.targetDate(),
        DateTimeFormatter.ISO_DATE
    );
  }

  @Override
  public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
    if (date == null) {
      return true;
    }
    return !date.isBefore(targetDate);
  }
}
