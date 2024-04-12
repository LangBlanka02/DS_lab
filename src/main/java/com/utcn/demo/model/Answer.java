package com.utcn.demo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Answer")
public class Answer {
    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    @ManyToOne()
    @JoinColumn(name="question_id")
    private Question questionId;
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User userId;
    @Column(name = "text")
    private String text;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public Answer() {}

    public Answer(Long answerId){
        this.answerId=answerId;
    }

    public Answer(Long answerId, User userId, Question questionId, String text, String pictureUrl, LocalDateTime creationDate) {
        this.answerId = answerId;
        this.userId = userId;
        this.questionId = questionId;
        this.text = text;
        this.pictureUrl = pictureUrl;
        this.creationDate = creationDate;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public User getUser() {
        return userId;
    }

    public void setUser(User userId) {
        this.userId = userId;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
