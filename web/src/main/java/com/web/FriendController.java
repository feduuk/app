package com.web;


import com.web.domain.Friend;
import com.web.domain.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Controller
@RequestMapping
public class FriendController {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${my.value.host.storage.link}")
    String storageHost;

    public List<Friend> getAllFriends(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        Friend[] friends = restTemplate.exchange(String.format("%s/storage/getAllFriends", storageHost), HttpMethod.GET, entity, Friend[].class).getBody();
        return Arrays.asList(friends);
    }

    @PostMapping("/info")
    public String getInfo(FriendModel friendModel, Boolean actuality, String token, Model model) {
        log.info("getInfo method of FriendController");
        log.info("Activities were chosen: " + friendModel);
        if(actuality == null) actuality = false;
        workerService.saveDataFromVk(token, actuality);
        List<String> checkedActivities = friendModel.getCheckedActivities();
        List<Friend> friends = getAllFriends();
        List<Response> responses = new ArrayList<>();
        friends.forEach(friend ->{
            Response response = new Response();
            response.setFriend(friend);
            List<String> checkedActivitiesOfFriend = new ArrayList<>();
            Set<String> activitiesOfFriend = new HashSet<>();
            for(Group group : friend.getGroups()){
                activitiesOfFriend.add(group.getActivity());
            }
            if(checkedActivities != null){
                checkedActivities.forEach(activity ->{
                    if(activitiesOfFriend.contains(activity)){
                        checkedActivitiesOfFriend.add(activity);
                    }
                });
            }
            response.setActivities(checkedActivitiesOfFriend);
            responses.add(response);
        });
        model.addAttribute("responses", responses);
        model.addAttribute("responseComparator", new ResponseComparator());
        model.addAttribute("checkedActivities", checkedActivities);
        return "result";
    }
}
