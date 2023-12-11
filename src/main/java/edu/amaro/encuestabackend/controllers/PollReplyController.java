package edu.amaro.encuestabackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.amaro.encuestabackend.models.request.PollReplyRequestModel;
import edu.amaro.encuestabackend.services.PollReplyService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/polls/reply")
public class PollReplyController {
    
    @Autowired
    PollReplyService pollReplyService;

    @PostMapping
    public void replyPoll(@RequestBody @Valid PollReplyRequestModel model){
        pollReplyService.createPollReply(model);
    }
}
