package edu.amaro.encuestabackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "poll_reply_details")
@Data
public class PollReplyDetailEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long questionId;

    private long answerId;

    @ManyToOne
    @JoinColumn(name = "poll_reply_id")
    private PollReplyEntity pollReply;
}
