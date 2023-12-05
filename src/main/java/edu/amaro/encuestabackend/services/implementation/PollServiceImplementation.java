package edu.amaro.encuestabackend.services.implementation;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import edu.amaro.encuestabackend.entities.AnswerEntity;
import edu.amaro.encuestabackend.entities.PollEntity;
import edu.amaro.encuestabackend.entities.QuestionEntity;
import edu.amaro.encuestabackend.entities.UserEntity;
import edu.amaro.encuestabackend.models.request.PollCreationRequestModel;
import edu.amaro.encuestabackend.repositories.PollRepository;
import edu.amaro.encuestabackend.repositories.UserRepository;
import edu.amaro.encuestabackend.services.PollService;

@Service
public class PollServiceImplementation  implements PollService{

    PollRepository pollRepository;
    UserRepository userRepository;

    public PollServiceImplementation(PollRepository pollRepository, UserRepository userRepository){
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String createPoll(PollCreationRequestModel model, String email) {

        UserEntity user = userRepository.findByEmail(email);

        ModelMapper mapper = new ModelMapper();

        PollEntity pollEntity = mapper.map(model, PollEntity.class);

        pollEntity.setUser(user);

        pollEntity.setPollId(UUID.randomUUID().toString());

        for (QuestionEntity question: pollEntity.getQuestions()){
            question.setPoll(pollEntity);
            for(AnswerEntity answer: question.getAnswers()){
                answer.setQuestion(question);
            }
        }

        pollRepository.save(pollEntity);

        return pollEntity.getPollId();
    }
    
}
