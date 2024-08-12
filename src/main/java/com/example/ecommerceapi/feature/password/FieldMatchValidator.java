package com.example.ecommerceapi.feature.password;

import com.example.ecommerceapi.feature.user.dto.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, UserRequest> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraint) {
        firstFieldName = constraint.first();
        secondFieldName = constraint.second();
    }

    @Override
    public boolean isValid(UserRequest value, ConstraintValidatorContext context) {
        final Object firstObj = switch (firstFieldName) {
            case "password" -> value.password();
            case "confirm_password" -> value.confirm_password();
            default -> throw new IllegalArgumentException("Invalid field name: " + firstFieldName);
        };
        final Object secondObj = switch (secondFieldName) {
            case "password" -> value.password();
            case "confirm_password" -> value.confirm_password();
            default -> throw new IllegalArgumentException("Invalid field name: " + secondFieldName);
        };

        if (firstObj == null && secondObj == null) {
            return true;
        } else if (firstObj != null && secondObj != null) {
            return firstObj.equals(secondObj);
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The password fields must match")
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation();
            return false;
        }
    }
}