package edu.amaro.encuestabackend.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import edu.amaro.encuestabackend.annotations.UniqueEmail;
import edu.amaro.encuestabackend.entities.UserEntity;
import edu.amaro.encuestabackend.repositories.UserRepository;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>{

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        UserEntity user = userRepository.findByEmail(value);
        if(user == null){
            return true;
        }
        return false;
    }
    
}
