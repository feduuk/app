package com.example.app.domain.controllers;

import com.example.app.domain.services.WorkerService;
import com.example.app.domain.repositories.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class Storage {
    private final WorkerService workerService;
    @Autowired
    Storage(WorkerService workerService){
        this.workerService = workerService;
    }
    @Bean
    CommandLineRunner commandLineRunner(FriendRepository repository){
        return args ->{
            repository.saveAll(
                    workerService.getFriendsFromVk()
            );
        };
    }
}
