package edu.amaro.encuestabackend.services;

import org.springframework.stereotype.Service;

import edu.amaro.encuestabackend.models.request.PollCreationRequestModel;

@Service
public interface PollService {
    
    public String createPoll(PollCreationRequestModel model, String email);
}
