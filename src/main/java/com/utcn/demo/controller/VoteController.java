package com.utcn.demo.controller;


import com.utcn.demo.model.User;
import com.utcn.demo.model.Vote;
import com.utcn.demo.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<Vote> retrieveAllVotes(){
        return this.voteService.retrieveVote();
    }

    @PostMapping("/insertVote")
    @ResponseBody
    public Vote insertVote(@RequestBody Vote vote){
        return this.voteService.insertVote(vote);
    }

    @PutMapping("/updateVote")
    @ResponseBody
    public Vote updateVote(@RequestBody Vote vote){
        return this.voteService.insertVote(vote);
    }

    @DeleteMapping("/deleteById")
    @ResponseBody
    public String deleteById(@RequestParam Long id){
        return this.voteService.deleteById(id);
    }

}
