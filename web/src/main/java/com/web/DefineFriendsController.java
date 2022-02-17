package com.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@SessionAttributes("token")
public class DefineFriendsController {

    @ModelAttribute("token")
    public Token getToken(){
        return new Token();
    }

    @RequestMapping(value="/defineFriends", method = { RequestMethod.GET, RequestMethod.POST })
    public String defineFriends(Model model, @ModelAttribute("token") String token){
        log.info("defineFriends method of DefineFriendsController");
        log.info("my token is {}", token);
        model.addAttribute(token);

        if(token == null){
            return "home";
        }
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
        model.addAttribute("activities", activities);
        return "defineFriends";
    }

}
