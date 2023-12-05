package edu.amaro.encuestabackend.models.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PollCreationRequestModel {

    @NotEmpty
    private String content;

    @NotNull
    private boolean isOpened;

    @Valid
    @NotEmpty
    @Size(min = 1, max = 30)
    private List<QuestionCreationRequestModel> questions;

}
