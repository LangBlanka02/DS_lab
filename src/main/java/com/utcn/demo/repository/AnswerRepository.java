package com.utcn.demo.repository;
import com.utcn.demo.model.Answer;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {

}
