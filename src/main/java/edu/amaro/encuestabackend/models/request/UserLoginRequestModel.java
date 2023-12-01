package edu.amaro.encuestabackend.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestModel {

    //@NotEmpty
    //@Email
    private String email;

    //@NotEmpty
    //@Size(min = 8, max = 40)
    private String password;
    
}
