package com.example.app.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

@Service
public class WorkerService {
    List<Friend> friends;
    public List<Friend> getFriendsFromVk(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        URL url1 = null;
        URL url2 = null;
        Set<Group> groups = null;
        try {
            url1 = new URL("https://api.vk.com/method/friends.get?count=20&fields=city,country,sex,movies,music,occupation,relation,activities,bdate,career,activities,interests,music,movies,tv,books,games,about&v=5.52&access_token=5f9a6e730a204c59774529615438829fb6b50abf5cf0dad40db223419faf52ceeed17598ade8006a22400");
            JsonNode jsonNode1 = objectMapper.readTree(url1);
            String str1 = jsonNode1.get("response").get("items").toString();
            friends = objectMapper.readValue(str1, new TypeReference<List<Friend>>(){});

            int user_id;
            JsonNode jsonNode2 = null;

            for (Friend friend : friends) {
                user_id = friend.getId();
                url2 = new URL(String.format("https://api.vk.com/method/users.getSubscriptions?fields=activity,description&user_id=%s&extended=1&v=5.52&access_token=5f9a6e730a204c59774529615438829fb6b50abf5cf0dad40db223419faf52ceeed17598ade8006a22400", user_id));
                jsonNode2 = objectMapper.readTree(url2);
                String str2 = null;
                JsonNode jn = jsonNode2.get("response");
                if (jn != null) {
                    groups = objectMapper.convertValue(jn.get("items"), new TypeReference<Set<Group>>() {
                    });
                    friend.setGroups(groups);
                }
            }

            //System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(friends));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return friends;
    }
}