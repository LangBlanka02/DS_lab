package com.utcn.demo.service;

import com.utcn.demo.model.User;
import com.utcn.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> retrieveUser(){
        return (List<User>)userRepository.findAll();
    }

    public User insertUser(User user){
        return this.userRepository.save(user);
    }

    public String deleteById(Long id){
        try{
            this.userRepository.deleteById(id);
            return ("deleted successfully");
        }catch (Exception e){
            return "deletion was NOT successful for user with id" +id;
        }
    }


}
