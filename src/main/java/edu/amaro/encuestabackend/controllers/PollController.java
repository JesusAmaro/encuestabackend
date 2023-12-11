package edu.amaro.encuestabackend.controllers;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.amaro.encuestabackend.entities.PollEntity;
import edu.amaro.encuestabackend.models.request.PollCreationRequestModel;
import edu.amaro.encuestabackend.models.responses.CreatedPollRest;
import edu.amaro.encuestabackend.models.responses.PaginatedPollRest;
import edu.amaro.encuestabackend.models.responses.PollRest;
import edu.amaro.encuestabackend.services.PollService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/polls") //   /polls?page=1&limit=10
public class PollController {

    @Autowired
    PollService pollService;

    @PostMapping
    public CreatedPollRest createPoll( @RequestBody @Valid PollCreationRequestModel pollCreationRequestModel, Authentication authentication){
        String pollId = pollService.createPoll(pollCreationRequestModel, authentication.getPrincipal().toString());
        return new CreatedPollRest(pollId);
    }

    @GetMapping(path = "/{id}/questions")
    public PollRest getPollWithQuestions(@PathVariable String id) {
        PollEntity poll = pollService.getPoll(id);

        ModelMapper mapper = new ModelMapper();

        return mapper.map(poll, PollRest.class);
    }

    @GetMapping()
    public PaginatedPollRest getPolls(
        @RequestParam(value = "page", defaultValue = "0") int page, 
        @RequestParam(value = "limit", defaultValue = "10") int limit,
        Authentication authentication
    ) {
        Page<PollEntity> paginatedPolls = pollService.getPolls(page, limit, authentication.getPrincipal().toString());
        
        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(PollEntity.class, PollRest.class).addMappings(m -> m.skip(PollRest::setQuestions));

        PaginatedPollRest paginatedPollRest = new PaginatedPollRest();

        paginatedPollRest.setPolls(
            paginatedPolls.getContent().stream().map(p -> mapper.map(p, PollRest.class)).collect(Collectors.toList())
        );

        paginatedPollRest.setTotalPages(paginatedPolls.getTotalPages());
        paginatedPollRest.setTotalRecords(paginatedPolls.getTotalElements());
        paginatedPollRest.setCurrentPageRecords(paginatedPolls.getNumberOfElements());
        paginatedPollRest.setCurrentPage(paginatedPolls.getPageable().getPageNumber() + 1); // Se le suma 1 pq las paginas inician en 0

        return paginatedPollRest;
    }

    @PatchMapping(path = "/{id}")
    public void togglePollOpened(@PathVariable String id, Authentication authentication) {
        pollService.togglePollOpened(id, authentication.getPrincipal().toString());
    }

    @DeleteMapping(path = "/{id}")
    public void deletePoll(@PathVariable String id, Authentication authentication) {
        pollService.deletePoll(id, authentication.getPrincipal().toString());
    }
    
    
}
