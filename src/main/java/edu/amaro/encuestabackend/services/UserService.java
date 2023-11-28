package edu.amaro.encuestabackend.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import edu.amaro.encuestabackend.entities.UserEntity;
import edu.amaro.encuestabackend.models.request.UserRegisterRequestModel;

@Service
public interface UserService extends UserDetailsService{
//public interface UserService {

    public UserDetails loadUserByUsername(String email);

    public UserEntity getUser(String email);
    
    public UserEntity createUser(UserRegisterRequestModel user);
}
