package com.utcn.demo.service;

import com.utcn.demo.model.Vote;
import com.utcn.demo.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    public List<Vote> retrieveVote(){
        return (List<Vote>)voteRepository.findAll();
    }

    public Vote insertVote(Vote vote){
        return this.voteRepository.save(vote);
    }

    public String deleteById(Long id){
        try{
            this.voteRepository.deleteById(id);
            return ("deleted successfully");
        }catch (Exception e){
            return "deletion was NOT successful for vote with id" +id;
        }
    }


}
