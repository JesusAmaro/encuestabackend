package edu.amaro.encuestabackend.models.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PollReplyRequestModel {

    @NotEmpty
    private String user;

    @NotNull
    private long poll;

    @Valid
    @NotEmpty
    @Size(min = 1)
    private List<PollReplyDetailRequestModel> pollReplies;
    
}
