package com.utcn.demo.service;

import com.utcn.demo.model.Answer;
import com.utcn.demo.model.Question;
import com.utcn.demo.model.User;
import com.utcn.demo.repository.AnswerRepository;
import com.utcn.demo.repository.QuestionRepository;
import com.utcn.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    public Answer retrieveAnswerById(Long id){
        Optional<Answer> answer=this.answerRepository.findById(id);
        if(answer.isPresent()){
            return answer.get();
        }else{
            return null;
        }
    }

    public List<Answer> retrieveAnswer(){
        return (List<Answer>) this.answerRepository.findAll();
    }

   public Answer insertAnswer(Answer answer){
        return this.answerRepository.save(answer);
    }



    public String deleteById(Long id){
        try{
            this.answerRepository.deleteById(id);
            return "answer deleted successfully";
        }catch (Exception e){
            return "Failed to delete answer with id "+id;
        }
    }

}
