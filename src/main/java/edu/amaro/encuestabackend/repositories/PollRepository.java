package edu.amaro.encuestabackend.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.amaro.encuestabackend.entities.PollEntity;
import edu.amaro.encuestabackend.interfaces.PollResult;


@Repository
public interface PollRepository extends CrudRepository<PollEntity, Long>{
    
    PollEntity findByPollId(String pollId);

    PollEntity findById(long id);

    Page<PollEntity> findAllByUserId(long id, Pageable pageable);
    //public List<PollEntity> findAllByUserId(long id);

    PollEntity findByPollIdAndUserId(String pollId, long userId);

    @Query(value = "select q.question_order as questionOrder, prd.question_id AS questionId, q.content as question, prd.answer_id AS answerId, a.content AS answer, COUNT(prd.answer_id) AS result FROM poll_replies pr LEFT JOIN poll_reply_details prd ON prd.poll_reply_id = pr.id left join answers a on a.id = prd.answer_id left join questions q on q.id = prd.question_id WHERE pr.poll_id = :pollId GROUP BY q.question_order, prd.question_id, prd.answer_id", nativeQuery = true)
    List<PollResult> getPollResults(@Param("pollId") long id);
}
