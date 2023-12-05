package edu.amaro.encuestabackend.validators;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import edu.amaro.encuestabackend.annotations.ValueOfEnum;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String>{
    
    private List<String> acceptedValues;

    @Override
    public void initialize(ValueOfEnum constraintAnnotation) {
        // TODO Auto-generated method stub
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return acceptedValues.contains(value.toString());
    }
}
