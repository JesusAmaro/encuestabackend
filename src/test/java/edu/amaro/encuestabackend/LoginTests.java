package edu.amaro.encuestabackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import edu.amaro.encuestabackend.models.request.UserLoginRequestModel;
import edu.amaro.encuestabackend.models.request.UserRegisterRequestModel;
import edu.amaro.encuestabackend.repositories.UserRepository;
import edu.amaro.encuestabackend.services.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginTests {
    
    private static final String API_LOGIN_URL = "/users/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    public void postLogin_sinCredenciales_retornaForbidden(){
        ResponseEntity<Object> response = login(null, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postLogin_conCredencialesIncorrectas_retornaUnauthorized(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail("asdfgba@mail.com");
        model.setPassword("12345678");
        ResponseEntity<Object> response = login(model, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postLogin_conCredencialesCorrectas_retornaOk(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        ResponseEntity<Object> response = login(model, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    
    @Test
    public void postLogin_conCredencialesCorrectas_retornaAuthToken(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        ResponseEntity<Map<String, String>> response = login(model, new ParameterizedTypeReference<Map<String, String>>(){});

        Map<String, String> body = response.getBody();
        String token = body.get("token");
        assertTrue(token.contains("Baerer"));;
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel model, Class<T> responseType){
        return testRestTemplate.postForEntity(API_LOGIN_URL, model, responseType);
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel model, ParameterizedTypeReference<T> responseType){
        HttpEntity<UserLoginRequestModel> entity = new HttpEntity<UserLoginRequestModel>(model, new HttpHeaders());
        return testRestTemplate.exchange(API_LOGIN_URL, HttpMethod.POST, entity, responseType);
    }
}
