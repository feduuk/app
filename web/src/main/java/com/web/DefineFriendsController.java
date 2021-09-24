package com.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping
public class DefineFriendsController {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${my.value.vk.token}")
    String token;
    @Value("${my.value.my.vk.id}")
    String vkId;

    @Value("${my.value.vk.app.id}")
    String appId;
    @Value("${my.value.vk.app.secret.key}")
    String appSecretKey;
    @Value("${my.value.host.web.link}")
    String webLink;


    @GetMapping(value="/processCode")
    public String processCode(@RequestParam String code, RedirectAttributes redirectAttributes){
        log.info("process code method of DefineFriendsController");
        VkResponse vkResponse = restTemplate.getForObject(String.format("https://oauth.vk.com/access_token?client_id=%s&client_secret=%s&redirect_uri=%s/processCode&code=%s", appId, appSecretKey, webLink, code), VkResponse.class);
        redirectAttributes.addFlashAttribute("vkResponse", vkResponse);
        return "redirect:defineFriends";
    }

    @GetMapping(value="/defineFriends")
    public String defineFriends(Model model, @ModelAttribute("vkResponse") VkResponse vkResponse){
        log.info("defineFriends method of DefineFriendsController");
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
//        VkResponse vkResponse = new VkResponse();
//        vkResponse.setAccess_token(token);
//        vkResponse.setUser_id(vkId);
//        model.addAttribute(vkResponse);
        model.addAttribute("activities", activities);
        return "defineFriends";
    }

}
