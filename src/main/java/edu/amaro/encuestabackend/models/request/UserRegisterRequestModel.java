package edu.amaro.encuestabackend.models.request;

import edu.amaro.encuestabackend.annotations.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequestModel {
    
    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    @UniqueEmail
    private String email;

    @NotEmpty
    @Size(min = 8, max = 40)
    private String password;
}
