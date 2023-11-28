package edu.amaro.encuestabackend.services.implementation;

import java.util.ArrayList;

//import javax.management.RuntimeErrorException;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.amaro.encuestabackend.entities.UserEntity;
import edu.amaro.encuestabackend.models.request.UserRegisterRequestModel;
import edu.amaro.encuestabackend.repositories.UserRepository;
import edu.amaro.encuestabackend.services.UserService;

@Service
public class UserImplementation implements UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserImplementation(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserEntity createUser(UserRegisterRequestModel user){
        UserEntity userEntity = new UserEntity();
        
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        UserEntity userVerify = userRepository.findByEmail(user.getEmail());
        if(userVerify != null){
            return null;
            //throw new RuntimeErrorException(null, "El correo ya se encuentra registrado");
        }
        return userRepository.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(email);
        }
        return new User(user.getEmail(), user.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserEntity getUser(String email) {
        return userRepository.findByEmail(email);
    }
    
}
