package edu.amaro.encuestabackend.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PollReplyDetailRequestModel {

    @NotNull
    @Positive
    private long questionId;

    @NotNull
    @Positive
    private long answerId;
    
}
