package edu.amaro.encuestabackend.entities;

import java.util.ArrayList;
import java.util.List;

import edu.amaro.encuestabackend.enums.QuestionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity(name = "questions")
@Data
public class QuestionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 255)
    private String content;

    @Column
    private int questionOrder;

    @Column
    private QuestionType type;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private PollEntity poll;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "question")
    private List<AnswerEntity> answers = new ArrayList<>();
}
