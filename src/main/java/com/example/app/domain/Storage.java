package com.example.app.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Controller
public class Storage {
    private final WorkerService workerService;
    @Autowired
    Storage(WorkerService workerService){
        this.workerService = workerService;
    }
    @Bean
    CommandLineRunner commandLineRunner(FriendRepository repository){
//        Collection<Group> groups = new ArrayList<>();
//
//        Group group1 = new Group();
//        group1.setDescription("sport");
//        group1.setId(1);
//        Group group2 = new Group();
//        group2.setDescription("travel");
//        group2.setId(2);
//
//        groups.add(group1);
//        groups.add(group2);
//
//
//        Collection<Friend> friends = new ArrayList<>();
//
//        Friend friend1 = new Friend();
//        friend1.setFirst_name("fedya");
//        friend1.setId(11);
//        friend1.setGroups(groups);
//
//        friends.add(friend1);

        return args ->{
//            repository.saveAll(
//                    //workerService.getFriendsFromVk()
//            );
        };
    }





//    @Bean
//    CommandLineRunner commandLineRunner2(GroupRepository repository){
////        Set<Group> groups = new HashSet<>();
////        for(Friend friend : workerService.getFriendsFromVk()){
////            for(Group group : friend.getGroups()){
////                groups.add(group);
////            }
////        }
//        return args ->{
////            repository.saveAll(
////                groups
////            );
//        };
//    }
}
