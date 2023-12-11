package edu.amaro.encuestabackend.models.responses;

import java.util.List;

import lombok.Data;

@Data
public class PollRest {
    
    private long id;

    private String pollId;

    private String content;

    private boolean opened;

    //list questions
    private List<QuestionRest> questions;
}
