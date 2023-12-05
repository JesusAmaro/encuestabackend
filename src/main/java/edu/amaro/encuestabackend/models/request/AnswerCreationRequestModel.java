package edu.amaro.encuestabackend.models.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnswerCreationRequestModel {
    
    @NotEmpty
    private String content;
}
