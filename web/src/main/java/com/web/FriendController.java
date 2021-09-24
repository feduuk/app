package com.web;


import com.web.domain.Friend;
import com.web.domain.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @PostMapping("/info")
    public String getInfo(FriendModel friendModel, Boolean actuality, String token, Model model) {
        log.info("getInfo method of FriendController");
        log.info("Activities were chosen: " + friendModel);
        if(token == null){
            return "home";
        }
        if(actuality == null) actuality = false;
        List<String> checkedActivities = friendModel.getCheckedActivities();
        List<Friend> friends = workerService.getDataFromVk(token, actuality);
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
