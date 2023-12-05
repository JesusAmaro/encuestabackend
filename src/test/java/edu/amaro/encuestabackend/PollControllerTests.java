package edu.amaro.encuestabackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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

import edu.amaro.encuestabackend.entities.PollEntity;
import edu.amaro.encuestabackend.models.request.PollCreationRequestModel;
import edu.amaro.encuestabackend.models.request.UserLoginRequestModel;
import edu.amaro.encuestabackend.models.request.UserRegisterRequestModel;
import edu.amaro.encuestabackend.models.responses.ValidationErrors;
import edu.amaro.encuestabackend.repositories.PollRepository;
import edu.amaro.encuestabackend.repositories.UserRepository;
import edu.amaro.encuestabackend.services.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class PollControllerTests {
    
    private static final String API_URL = "/polls";

    private static final String API_LOGIN_URL = "/users/login";

    private String token = "";
    
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PollRepository pollRepository;

    @BeforeAll
    public void initializeObjects(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        ResponseEntity<Map<String, String>> response = login(model, new ParameterizedTypeReference<Map<String, String>>(){});

        Map<String, String> body = response.getBody();
        this.token = body.get("token");
    }

    @AfterEach
    public void cleanUp(){
        pollRepository.deleteAll();
    }

    @AfterAll
    public void cleanAfter(){
        userRepository.deleteAll();
    }

    public <T> ResponseEntity<T> createPoll(PollCreationRequestModel model, Class<T> responseType){
        return testRestTemplate.postForEntity(API_URL, model, responseType);
    }

    public <T> ResponseEntity<T> createPoll(PollCreationRequestModel model, ParameterizedTypeReference<T> responseType){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<PollCreationRequestModel> entity = new HttpEntity<PollCreationRequestModel>(model, headers);
        return testRestTemplate.exchange(API_URL, HttpMethod.POST, entity, responseType);
    }

    @Test
    public void createPoll_sinAuthenticacion_retornaForbidden(){
        ResponseEntity<Object> response = createPoll(new PollCreationRequestModel(), Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }
    
    @Test
    public void createPoll_conAuthenticacionSinDatos_retornaBadRequest(){
        ResponseEntity<ValidationErrors> response = createPoll(new PollCreationRequestModel(), new ParameterizedTypeReference<ValidationErrors>(){});
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    
    @Test
    public void createPoll_conAuthenticacionSinContenidoDeLaEncuesta_retornaBadRequest(){
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.setContent("");
        ResponseEntity<ValidationErrors> response = createPoll(new PollCreationRequestModel(), new ParameterizedTypeReference<ValidationErrors>(){});
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_conAuthenticacionSinPreguntasDeLaEncuesta_retornaBadRequest(){
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.setQuestions(null);
        ResponseEntity<ValidationErrors> response = createPoll(new PollCreationRequestModel(), new ParameterizedTypeReference<ValidationErrors>(){});
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_conAuthenticacionConPreguntaSinConenido_retornaBadRequest(){
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setContent("");
        ResponseEntity<ValidationErrors> response = createPoll(new PollCreationRequestModel(), new ParameterizedTypeReference<ValidationErrors>(){});
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_conAuthenticacionConPreguntaConOrdenIncorrecto_retornaBadRequest(){
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setQuestionOrder(0);
        ResponseEntity<ValidationErrors> response = createPoll(new PollCreationRequestModel(), new ParameterizedTypeReference<ValidationErrors>(){});
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_conAuthenticacionConPreguntaConQuestionTypeIncorrecto_retornaBadRequest(){
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setType("numa");
        ResponseEntity<ValidationErrors> response = createPoll(new PollCreationRequestModel(), new ParameterizedTypeReference<ValidationErrors>(){});
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_conAuthenticacionConPreguntaSinRespuestas_retornaBadRequest(){
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setAnswers(null);
        ResponseEntity<ValidationErrors> response = createPoll(new PollCreationRequestModel(), new ParameterizedTypeReference<ValidationErrors>(){});
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    // @Test
    // public void createPoll_conAuthenticacionConPreguntaSinContenidoEnLaRespuesta_retornaBadRequest(){
    //     PollCreationRequestModel poll = TestUtil.createValidPoll();
    //     poll.getQuestions().get(0).getAnswers().get(0).setContent("");
    //     ResponseEntity<ValidationErrors> response = createPoll(new PollCreationRequestModel(), new ParameterizedTypeReference<ValidationErrors>(){});
    //     assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    // }

    // @Test
    // public void createPoll_conAutenticacionConEncuestaValida_retornaCreatedPollId() {
    //     PollCreationRequestModel poll = TestUtil.createValidPoll();
    //     ResponseEntity<Map<String,String>> response = createPoll(
    //         poll, new ParameterizedTypeReference<Map<String,String>>(){}
    //     );
    //     Map<String,String> body = response.getBody();
    //     assertTrue(body.containsKey("pollId"));
    // }

    // @Test
    // public void createPoll_conAutenticacionConEncuestaValida_guardaEnBaseDeDatos() {
    //     PollCreationRequestModel poll = TestUtil.createValidPoll();
    //     ResponseEntity<Map<String,String>> response = createPoll(
    //         poll, new ParameterizedTypeReference<Map<String,String>>(){}
    //     );
    //     Map<String,String> body = response.getBody();
    //     PollEntity pollDB = pollRepository.findByPollId(body.get("pollId"));
    //     assertNotNull(pollDB);
    // }

    public <T> ResponseEntity<T> login(UserLoginRequestModel model, ParameterizedTypeReference<T> responseType){
        HttpEntity<UserLoginRequestModel> entity = new HttpEntity<UserLoginRequestModel>(model, new HttpHeaders());
        return testRestTemplate.exchange(API_LOGIN_URL, HttpMethod.POST, entity, responseType);
    }

}
