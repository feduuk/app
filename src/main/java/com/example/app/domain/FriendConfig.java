package com.example.app.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FriendConfig {
    @Bean
    CommandLineRunner commandLineRunner(FriendRepository repository){


        return args ->{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            URL url1 = null;
            URL url2 = null;
            List<Friend> friends = null;
            List<Friend.Group> groups = null;
            try {
                url1 = new URL("https://api.vk.com/method/friends.get?fields=city,country,sex,movies,music,occupation,relation,activities,bdate,career,activities,interests,music,movies,tv,books,games,about&v=5.52&access_token=5f9a6e730a204c59774529615438829fb6b50abf5cf0dad40db223419faf52ceeed17598ade8006a22400");
                JsonNode jsonNode1 = objectMapper.readTree(url1);
                String str1 = jsonNode1.get("response").get("items").toString();
                friends = objectMapper.readValue(str1, new TypeReference<List<Friend>>(){});

                int user_id;
                JsonNode jsonNode2 = null;
//                for(Friend friend : friends){
//                    user_id = friend.getId();
//                    //System.out.println(user_id);
//                    url2 = new URL(String.format("https://api.vk.com/method/groups.get?user_id=%s&extended=1&v=5.52&access_token=5f9a6e730a204c59774529615438829fb6b50abf5cf0dad40db223419faf52ceeed17598ade8006a22400", user_id));
//                    jsonNode2 = objectMapper.readTree(url2);
//                    String str2 = null;
//                    JsonNode jn = jsonNode2.get("response");
//                    if(jn != null){
//                        groups = objectMapper.convertValue(jn.get("items"), new TypeReference<List<Friend.Group>>(){});
//                        friend.setGroups(groups);
//                    }
//                }



                //System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(friends));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }


            repository.saveAll(
                    friends
            );
        };
    }
}
