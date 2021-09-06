package com.web;

import com.domain.Friend;
import com.storage.FriendRepository;
import com.storage.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping
public class DefineFriendsController {
    private final GroupRepository groupRepository;
    @Autowired
    public DefineFriendsController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping("/defineFriends")
    public String defineFriends(Model model){
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
        return "defineFriends";
    }
    @PostMapping("/defineFriends")
    public String processFriends(FriendModel friendModel){
        //List<String> checkedActivities=null;
        log.info("Activities were chosen: " + friendModel);
        return "forward:/info";
    }
}
