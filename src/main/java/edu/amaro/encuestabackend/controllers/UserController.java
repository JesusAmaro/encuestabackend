package edu.amaro.encuestabackend.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.amaro.encuestabackend.entities.UserEntity;
import edu.amaro.encuestabackend.models.request.UserRegisterRequestModel;
import edu.amaro.encuestabackend.models.responses.UserRest;
import edu.amaro.encuestabackend.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;
    
    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRegisterRequestModel userModel) {
        UserEntity user = userService.createUser(userModel);
        if(user == null){
            return ResponseEntity.badRequest().body("{\"error\": \"El correo ya se encuentra registrado\"}");
        }
        UserRest userRest = new UserRest();
        BeanUtils.copyProperties(user, userRest);
        return ResponseEntity.ok(userRest);
    }
    
    @GetMapping
    public UserRest getUser(Authentication authentication){
        UserEntity user = userService.getUser(authentication.getPrincipal().toString());
        UserRest userRest = new UserRest();
        BeanUtils.copyProperties(user, userRest);
        return userRest;
    }
}
