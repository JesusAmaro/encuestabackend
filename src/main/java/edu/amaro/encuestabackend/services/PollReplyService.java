package edu.amaro.encuestabackend.services;

import edu.amaro.encuestabackend.models.request.PollReplyRequestModel;

public interface PollReplyService {
    
    public void createPollReply(PollReplyRequestModel model);
}
