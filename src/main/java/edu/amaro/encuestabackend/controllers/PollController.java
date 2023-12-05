package edu.amaro.encuestabackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.amaro.encuestabackend.models.request.PollCreationRequestModel;
import edu.amaro.encuestabackend.models.responses.CreatedPollRest;
import edu.amaro.encuestabackend.services.PollService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/polls")
public class PollController {

    @Autowired
    PollService pollService;

    @PostMapping
    public CreatedPollRest createPoll( @RequestBody @Valid PollCreationRequestModel pollCreationRequestModel, Authentication authentication){
        String pollId = pollService.createPoll(pollCreationRequestModel, authentication.getPrincipal().toString());
        return new CreatedPollRest(pollId);
    }
}
