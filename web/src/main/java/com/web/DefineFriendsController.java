package com.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@SessionAttributes("vkResponse")
public class DefineFriendsController {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private RestTemplate restTemplate;

//    @Value("${my.value.vk.token}")
//    String token;
//    @Value("${my.value.my.vk.id}")
//    String vkId;

    @Value("${my.value.vk.app.id}")
    String appId;
    @Value("${my.value.vk.app.secret.key}")
    String appSecretKey;
    @Value("${my.value.host.web.link}")
    String webLink;

    @ModelAttribute("vkResponse")
    public VkResponse vkResponse() {
        return new VkResponse();
    }

    @GetMapping(value="/processCode")
    public String processCode(@RequestParam String code, @ModelAttribute("vkResponse") VkResponse vkResponse, Model model){
        log.info("process code method of DefineFriendsController");
        vkResponse = restTemplate.getForObject(String.format("https://oauth.vk.com/access_token?client_id=%s&client_secret=%s&redirect_uri=%s/processCode&code=%s", appId, appSecretKey, webLink, code), VkResponse.class);
        model.addAttribute(vkResponse);
        return "redirect:defineFriends";
    }

    @GetMapping(value="/defineFriends")
    public String defineFriends(Model model, @ModelAttribute("vkResponse") VkResponse vkResponse){
        log.info("defineFriends method of DefineFriendsController");

//        vkResponse.setAccess_token(token);
//        vkResponse.setUser_id(vkId);
//        model.addAttribute(vkResponse);

        if(vkResponse.getAccess_token() == null){
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
