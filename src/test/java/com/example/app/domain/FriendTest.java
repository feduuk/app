package com.example.app.domain;

import com.example.app.domain.entities.Friend;
import com.example.app.domain.entities.Group;
import com.example.app.domain.repositories.FriendRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class FriendTest {
    @Autowired
    FriendRepository friendRepository;
    @Test
    public void saveStudent(){
        Group group1 = Group.builder()
                .id(1)
                .groupName("sport")
                .build();
        Group group2 = Group.builder()
                .id(2)
                .groupName("fashion")
                .build();
        Group group3 = Group.builder()
                .id(3)
                .groupName("tech")
                .build();
        Friend friend1 = Friend.builder()
                .id(1)
                .first_name("josh")
                .last_name("brown")
                .groups(Arrays.asList(group1, group3))
                .build();
        Friend friend2 = Friend.builder()
                .id(2)
                .first_name("mark")
                .last_name("bush")
                .groups(Arrays.asList(group2))
                .build();
        friendRepository.saveAll(Arrays.asList(friend1, friend2));

    }
}