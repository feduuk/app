package com.web;

import com.domain.Friend;
import com.storage.FriendRepository;
import com.storage.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping
public class DefineFriendsController {
    private final GroupRepository groupRepository;
    private final FriendRepository friendRepository;
    private final WorkerService workerService;

    VkResponse response;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public DefineFriendsController(GroupRepository groupRepository, FriendRepository friendRepository, WorkerService workerService) {
        this.groupRepository = groupRepository;
        this.friendRepository = friendRepository;
        this.workerService = workerService;
    }

//    @GetMapping("/defineFriends")
//    public String defineFriends(Model model){
//        //List<String> activities = groupRepository.findActivities();
//        String token = "e8d5e2366e9cca21f7c83ba93088e7118efd53f8a01f2576d94b3b8e6127b0d522c75a496eb4e8ff638b4";
//        friendRepository.saveAll(workerService.getFriendsFromVk(token));
//        List<String> activities = new ArrayList<>(Arrays.asList("Автомобили",
//                "Активный отдых",
//                "Бизнес",
//                "Видеоигры",
//                "Дизайн и графика",
//                "Домашние и дикие животные",
//                "Здоровый образ жизни",
//                "Искусство и развлечения",
//                "История",
//                "Киберспорт",
//                "Кино",
//                "Кулинария",
//                "Кулинария, рецепты",
//                "Культура",
//                "Литература",
//                "Музыка",
//                "Наука",
//                "Политика",
//                "Программирование",
//                "Творчество",
//                "Туризм, путешествия",
//                "Философия",
//                "Юмор"
//        ));
//
//        FriendModel friendModel = new FriendModel();
//        model.addAttribute("activities", activities);
//        model.addAttribute("friendModel", friendModel);
//        return "defineFriends";
//    }


    @GetMapping(value="/processCode")
    public String processCode(@RequestParam String code){
        log.info("!defineFriends");
        response = restTemplate.getForObject(String.format("https://oauth.vk.com/access_token?client_id=7914980&client_secret=FQQsAPkXfHP3D2MhRHin&redirect_uri=http://34.136.208.204:8080/processCode&code=%s", code), VkResponse.class);
        return "redirect:defineFriends";
    }

    @GetMapping(value="/defineFriends")
    public String defineFriends(Model model){
        log.info("access_token: " + response.getAccess_token());
        log.info("expires_in: " + response.getExpires_in());
        log.info("user_id: " + response.getUser_id());

        friendRepository.saveAll(workerService.getFriendsFromVk(response.getAccess_token()));

        //List<String> activities = groupRepository.findActivities();
        List<String> activities = new ArrayList<>(Arrays.asList("Автомобили",
                "Активный отдых",
                "Бизнес",
                "Видеоигры",
                "Дизайн и графика",
                "Домашние и дикие животные",
                "Здоровый образ жизни",
                "Искусство и развлечения",
                "История",
                "Киберспорт",
                "Кино",
                "Кулинария",
                "Кулинария, рецепты",
                "Культура",
                "Литература",
                "Музыка",
                "Наука",
                "Политика",
                "Программирование",
                "Творчество",
                "Туризм, путешествия",
                "Философия",
                "Юмор"
        ));

        FriendModel friendModel = new FriendModel();
        model.addAttribute("activities", activities);
        model.addAttribute("friendModel", friendModel);
        model.addAttribute("vkReference", String.format("https://vk.com/id%s", response.getUser_id()));
        return "defineFriends";
    }

    @PostMapping("/defineFriends")
    public String processFriends(FriendModel friendModel){
        //List<String> checkedActivities=null;
        log.info("Activities were chosen: " + friendModel);
        return "forward:/info";
    }

}
