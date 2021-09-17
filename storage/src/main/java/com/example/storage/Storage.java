package com.example.storage;

import com.domain.Friend;
import com.example.storage.repositories.FriendRepository;
import com.example.storage.repositories.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/storage")
public class Storage {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private FriendRepository friendRepository;




    @GetMapping("/getFriend/{id}")
    public Friend getFriend(@PathVariable int id){
        return friendRepository.findById(id);
    }
    @GetMapping("/getAllFriends")
    public List<Friend> getAllFriends(){
        return friendRepository.findAll();
    }


    @PostMapping("/saveFriend")
    public Friend saveFriend(@RequestBody Friend friend){
        return friendRepository.save(friend);
    }
    @PostMapping("/saveAllFriends")
    public List<Friend> saveAllFriends(@RequestBody List<Friend> friends){
        for(Friend friend : friends){
            log.info("date: " + friend.getUpdateDate());
        }
        return friendRepository.saveAll(friends);
    }

}
