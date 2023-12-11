package edu.amaro.encuestabackend.services.implementation;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import edu.amaro.encuestabackend.entities.PollEntity;
import edu.amaro.encuestabackend.entities.PollReplyDetailEntity;
import edu.amaro.encuestabackend.entities.PollReplyEntity;
import edu.amaro.encuestabackend.models.request.PollReplyRequestModel;
import edu.amaro.encuestabackend.repositories.PollReplyRepository;
import edu.amaro.encuestabackend.repositories.PollRepository;
import edu.amaro.encuestabackend.services.PollReplyService;

@Service
public class PollReplyImplementaion  implements PollReplyService{

    PollReplyRepository pollReplyRepository;
    PollRepository pollRepository;

    public PollReplyImplementaion(PollReplyRepository pollReplyRepository, PollRepository pollRepository){
        this.pollReplyRepository = pollReplyRepository;
        this.pollRepository = pollRepository;
    }

    @Override
    public void createPollReply(PollReplyRequestModel model) {
        
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setAmbiguityIgnored(true);

        PollReplyEntity pollReply = mapper.map(model, PollReplyEntity.class);

        PollEntity poll = pollRepository.findById(model.getPoll());

        pollReply.setPoll(poll);

        //Map<Long, Long> uniqueReplies = new HashMap<>();
        Set<Long> uniqueReplies = new HashSet<>();

        for(PollReplyDetailEntity pollReplyDetailEntity: pollReply.getPollReplies()){
            pollReplyDetailEntity.setPollReply(pollReply);
            uniqueReplies.add(pollReplyDetailEntity.getQuestionId());
        }

        if(uniqueReplies.size() != poll.getQuestions().size()){
            throw new RuntimeException("You must answer all the questions");
        }

        pollReplyRepository.save(pollReply);

    }
    
}
