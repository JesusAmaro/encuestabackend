package edu.amaro.encuestabackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.amaro.encuestabackend.entities.PollEntity;


@Repository
public interface PollRepository extends CrudRepository<PollEntity, Long>{
    
    PollEntity findByPollId(String pollId);

    PollEntity findById(long id);

    Page<PollEntity> findAllByUserId(long id, Pageable pageable);
    //public List<PollEntity> findAllByUserId(long id);

    PollEntity findByPollIdAndUserId(String pollId, long userId);
}
