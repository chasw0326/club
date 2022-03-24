package com.example.club_project.util;


import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

public class ValidateUtil {

    @Autowired
    private Validator validator;

    // 예외 종류는 바꿔도 되지만 RuntimeException 계열로 바꾸거면 상관없지만
    // checked 예외로 바꾼다면 rollbackFor = "예외" 코드를 추가 할 것
    public void validate(Object entity){
        Set<ConstraintViolation<Object>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Object> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb, violations);
        }
    }
}
