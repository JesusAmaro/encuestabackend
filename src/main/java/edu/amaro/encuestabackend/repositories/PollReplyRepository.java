package edu.amaro.encuestabackend.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.amaro.encuestabackend.entities.PollReplyEntity;

public interface PollReplyRepository extends CrudRepository<PollReplyEntity, Long>{
    
}
