package gr.aueb.cf.schoolpro.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidatorUtil {

    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private ValidatorUtil() {}

    public static <T> List<String> validateDTO(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        List<String> errors = new ArrayList<>();

        if (!violations.isEmpty()) {
            for (ConstraintViolation<T> violation : violations) {
                errors.add(violation.getMessage());
            }
        }
        return errors;
    }
}
