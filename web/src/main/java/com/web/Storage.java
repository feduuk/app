package com.web;

import com.storage.FriendRepository;
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
//    private final WorkerService2 workerService2;
//
//    @Autowired
//    Storage(WorkerService2 workerService2) {
//        this.workerService2 = workerService2;
//    }

    @Bean
    CommandLineRunner commandLineRunner(FriendRepository repository) {
        return args -> {
            String token = "e8d5e2366e9cca21f7c83ba93088e7118efd53f8a01f2576d94b3b8e6127b0d522c75a496eb4e8ff638b4";
//            repository.saveAll(
//                    workerService2.getFriendsFromVk()
//            );

            repository.saveAll(
                    workerService.getFriendsFromVk(token)
            );
        };
    }
}
