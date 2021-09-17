package com.web;


import com.domain.Friend;
import com.domain.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@RestController
@RequestMapping
public class FriendController {


    @Autowired
    private RestTemplate restTemplate;

    //    @GetMapping("/info")
//    public List<Friend> getInfo(){
//        return friendRepository.findAll();
//    }


//    @PostMapping("/info")
//    public List<Integer> getInfo(FriendModel friendModel) {
//        log.info("Activities were chosen: " + friendModel);
////        List<String> list = new ArrayList<>();
////        list.add("Творчество");
//        return friendRepository.findFriendsIdByGroupActivity(friendModel.getCheckedActivities());
////        return friendRepository.findFriendsIdByGroupActivity();
//    }


    @PostMapping("/info")
    public List<Response> getInfo(FriendModel friendModel) {
        log.info("Activities were chosen: " + friendModel);
        List<String> checkedActivities = friendModel.getCheckedActivities();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <List<String>> entity = new HttpEntity<>(headers);
        Friend[] friendsArray = restTemplate.exchange("http://localhost:8081/storage/getAllFriends", HttpMethod.GET, entity, Friend[].class).getBody();
        List<Friend> friends = Arrays.asList(friendsArray);
        List<Response> responses = new ArrayList<>();
        friends.forEach(friend ->{
            Response response = new Response();
            response.setFriend(friend);
            List<String> checkedActivitiesOfFriend = new ArrayList<>();
            Set<String> activitiesOfFriend = new HashSet<>();
            for(Group group : friend.getGroups()){
                activitiesOfFriend.add(group.getActivity());
            }
            checkedActivities.forEach(activity ->{
                if(activitiesOfFriend.contains(activity)){
                    checkedActivitiesOfFriend.add(activity);
                }
            });
            response.setActivities(checkedActivitiesOfFriend);
            responses.add(response);
        });
        return responses;
    }

}
