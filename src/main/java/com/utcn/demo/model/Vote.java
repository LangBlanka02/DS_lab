package com.utcn.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name="Vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

   /* @ManyToOne
    @JoinColumn(name = "question_id", nullable = true)
    private Question questionId;
*/
    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = true)
    private Answer answerId;

    private boolean upvote;
    private boolean downvote;

    public Vote() {}


    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long id) {
        this.voteId = id;
    }

    public User getUser() {
        return userId;
    }

    public void setUser(User user) {
        this.userId = user;
    }

   /* public Question getQuestion() {
        return questionId;
    }

    public void setQuestion(Question question) {
        this.questionId = question;
        this.answerId = null;
    }

    public Answer getAnswer() {
        return answerId;
    }

    public void setAnswer(Answer answer) {
        this.answerId = answer;
        this.questionId = null;
    }*/

    public boolean isUpvote() {
        return upvote;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
        this.downvote = !upvote;
    }

    public boolean isDownvote() {
        return downvote;
    }

    public void setDownvote(boolean downvote) {
        this.downvote = downvote;
        this.upvote = !downvote;
    }
}
