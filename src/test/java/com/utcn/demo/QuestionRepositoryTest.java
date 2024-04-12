package com.utcn.demo;

import com.utcn.demo.model.Question;
import com.utcn.demo.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository; // Assumes this extends CrudRepository

    @Test
    public void testQuestionUserAssociation() {
        // Assume there's a question with ID 1 in your database
        Long questionId = 1L;
        Question question = questionRepository.findById(questionId).orElse(null);

        assertNotNull(question, "Question not found");
        assertNotNull(question.getUser(), "Associated User not found");
        // Accessing User's properties to ensure they are loaded
        System.out.println("User ID: " + question.getUser().getUserId());
        System.out.println("Username: " + question.getUser().getUsername());
    }
}
