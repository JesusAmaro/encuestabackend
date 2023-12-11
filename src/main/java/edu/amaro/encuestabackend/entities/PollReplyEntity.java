package edu.amaro.encuestabackend.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@EntityListeners(AuditingEntityListener.class)
@Entity(name = "poll_replies")
@Data
public class PollReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String user;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private PollEntity poll;

    @CreatedDate
    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "pollReply")
    private List<PollReplyDetailEntity> pollReplies = new ArrayList<>();
    
}
