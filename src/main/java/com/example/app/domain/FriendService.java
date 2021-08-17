package com.example.app.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    @Autowired
    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    public List<Friend> getFriends(){
        return friendRepository.findAll();
    }
}
