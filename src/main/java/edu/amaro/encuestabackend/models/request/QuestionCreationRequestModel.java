package edu.amaro.encuestabackend.models.request;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import edu.amaro.encuestabackend.annotations.ValueOfEnum;
import edu.amaro.encuestabackend.enums.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionCreationRequestModel {
    
    @NotEmpty
    private String content;

    @NotNull
    @Range(min = 1, max = 30)
    private int questionOrder;

    @NotEmpty
    @ValueOfEnum(enumClass = QuestionType.class)
    private String type;

    @Valid
    @NotEmpty
    @Size(min = 1, max = 10)
    private List<AnswerCreationRequestModel> answers;

}
