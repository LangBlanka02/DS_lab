package com.utcn.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Question")
public class Question {
    @Id
    @Column(name="question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User userId;
    @Column(name="title")
    private String title;
    @Column(name="text")
    private String text;
    @Column(name="creation_date")
    private LocalDateTime creationDate;
    @Column(name = "picture_url")
    private String pictureUrl;

    /*@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Question_Tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;


    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

*/
    public Question() {}

    public Question(Long questionId, User userId, String title, String text, LocalDateTime creationDate, String pictureUrl) {
        this.questionId = questionId;
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
        this.pictureUrl = pictureUrl;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId =questionId ;
    }

    public User getUser() {
        return userId;
    }

    public void setUser(User userId) {
        this.userId =userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
