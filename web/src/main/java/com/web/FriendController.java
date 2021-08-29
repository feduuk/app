package com.web;


import com.domain.Friend;
import com.storage.FriendRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class FriendController {

    private final FriendRepository friendRepository;

    @Autowired
    public FriendController(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    //    @GetMapping("/info")
//    public List<Friend> getInfo(){
//        return friendRepository.findAll();
//    }


    @PostMapping("/info")
    public List<Integer> getInfo(FriendModel friendModel) {
        log.info("Activities were chosen: " + friendModel);
//        List<String> list = new ArrayList<>();
//        list.add("Творчество");
        return friendRepository.findFriendsIdByGroupActivity(friendModel.getCheckedActivities());
//        return friendRepository.findFriendsIdByGroupActivity();
    }

}
