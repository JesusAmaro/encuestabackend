package edu.amaro.encuestabackend.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import edu.amaro.encuestabackend.entities.PollEntity;
import edu.amaro.encuestabackend.interfaces.PollResult;
import edu.amaro.encuestabackend.models.request.PollCreationRequestModel;

@Service
public interface PollService {
    
    public String createPoll(PollCreationRequestModel model, String email);

    public PollEntity getPoll(String pollId);

    public Page<PollEntity> getPolls(int page, int limit, String email);
    //public List<PollEntity> getPolls(int page, int limit, String email);

    public void togglePollOpened(String pollId, String email);

    public void deletePoll(String pollId, String email);

    public List<PollResult> getResults(String pollId, String email);
}
