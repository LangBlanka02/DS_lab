package com.utcn.demo.service;
import com.utcn.demo.model.Question;
import com.utcn.demo.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> retrieveQuestion(){
        return (List<Question>) this.questionRepository.findAll();
    }

    public Question retrieveQuestionById(Long id){
        Optional<Question> question=this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        }else{
            return null;
        }
    }

    public Question insertQuestion(Question question ){
        return this.questionRepository.save(question);
    }



    /*public String deleteById(Long questionId) {
        try{
            this.questionRepository.deleteById(questionId);
            return ("Question successfully deleted!");
        }catch (Exception e){
            return "Failed to delete question with id:" + questionId;
        }
    }*/



    public String deleteById(Long questionId) {
        if (!this.questionRepository.existsById(questionId)) {
            throw new EntityNotFoundException("Question with ID " + questionId + " does not exist.");
        }
        try {
            this.questionRepository.deleteById(questionId);
            return "Question successfully deleted!";
        } catch (Exception e) {
            // Log the exception details (consider using a logger here)
            throw new RuntimeException("Failed to delete question with id: " + questionId, e);
        }
    }


}

