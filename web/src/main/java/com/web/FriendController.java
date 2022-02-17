package com.web;


import com.web.domain.Friend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
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

    @RequestMapping(value="/info", method = { RequestMethod.GET, RequestMethod.POST })
    public String getInfo(FriendModel friendModel, Boolean actuality, Model model, HttpSession session) {
        log.info("getInfo method of FriendController");
        log.info("Activities were chosen: " + friendModel);
        boolean foundAtLeastOneChatmate = false;
        String token = ((Token)session.getAttribute("token")).getToken();
        if(token == null){
            return "home";
        }
        if(actuality == null) actuality = false;
        List<String> checkedActivities = friendModel.getCheckedActivities();
        List<Friend> friends = workerService.getDataFromVk(token, actuality);
        List<Response> responses = new ArrayList<>();
        for(Friend friend : friends){
            Response response = new Response();
            response.setFriend(friend);
            List<String> checkedActivitiesOfFriend = new ArrayList<>();
            Set<String> activitiesOfFriend = new HashSet<>();
            friend.getGroups().forEach(group -> {
                activitiesOfFriend.add(group.getActivity());
            });
            if(checkedActivities != null){
                for(String activity : checkedActivities){
                    if(activitiesOfFriend.contains(activity)){
                        checkedActivitiesOfFriend.add(activity);
                        foundAtLeastOneChatmate = true;
                    }
                }
            }
            response.setActivities(checkedActivitiesOfFriend);
            responses.add(response);
        }
        model.addAttribute("foundAtLeastOneChatmate", foundAtLeastOneChatmate);
        model.addAttribute("responses", responses);
        model.addAttribute("responseComparator", new ResponseComparator());
        model.addAttribute("checkedActivities", checkedActivities);
        return "result";
    }
}
