package kr.pravusid.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatcher, Object> {

    private String baseField;
    private String matchField;
    private String message;

    @Override
    public void initialize(FieldsMatcher constraint) {
        baseField = constraint.baseField();
        matchField = constraint.matchField();
        message = constraint.message();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Object baseFieldValue = getFieldValue(object, baseField);
            Object matchFieldValue = getFieldValue(object, matchField);
            if (baseFieldValue != null && baseFieldValue.equals(matchFieldValue)) {
                return true;
            }
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(baseField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

}
