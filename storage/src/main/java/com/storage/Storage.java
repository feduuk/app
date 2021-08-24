package com.storage;

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
