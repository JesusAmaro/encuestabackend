package edu.amaro.encuestabackend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.amaro.encuestabackend.entities.PollEntity;

@Repository
public interface PollRepository extends CrudRepository<PollEntity, Long>{
    
    public PollEntity findByPollId(String pollId);
}
