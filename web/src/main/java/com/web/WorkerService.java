package com.web;

import com.domain.Friend;
import com.domain.Group;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class WorkerService {

    FriendRepository friendRepository;

    @Autowired
    public WorkerService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    private long getUpdatedTimeDiffForCurrentFriend(Friend friendInDb) {
        Date date = friendInDb.getUpdateDate();
        Date nowDate = new Date();
        long diffInMillies = nowDate.getTime() - date.getTime();
        return TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public List<Friend> getFriendsFromVk(String token){
        List<Friend> friends;
        List<Friend> friendsForSaving = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        URL url1 = null;

        String fullInfo = String.format("https://api.vk.com/method/friends.get?count=20&fields=sex,bdate,occupation&v=5.100&access_token=%s", token);
        List<Integer> ids = new ArrayList<>();
        try {
            url1 = new URL(fullInfo);
            JsonNode jsonNode1 = objectMapper.readTree(url1);
            String str1 = jsonNode1.get("response").get("items").toString();
            friends = objectMapper.readValue(str1, new TypeReference<List<Friend>>(){});
            for(Friend friend : friends){
                ids.add(friend.getId());
            }
            List<Integer> idsInDB = friendRepository.findAllId();

            friends.forEach(friend -> {
                try {
                    List<Group> groups = null;
                    Friend friendInDb = friendRepository.findById(friend.getId());
                    if (friendInDb == null || getUpdatedTimeDiffForCurrentFriend(friendInDb) > 24) {
                        URL url2 = new URL(String.format("https://api.vk.com/method/users.getSubscriptions?fields=activity,description&user_id=%s&extended=1&v=5.100&access_token=%s", friend.getId(), token));
                        JsonNode jsonNode2 = objectMapper.readTree(url2);
                        JsonNode jn = jsonNode2.get("response");
                        if (jn != null) {
                            groups = objectMapper.convertValue(jn.get("items"), new TypeReference<List<Group>>() {});
                            friend.setGroups(groups);
                        }
                        friend.setUpdateDate(new Date());
                        friendsForSaving.add(friend);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return friendsForSaving;
    }







    public List<Friend> getFriendsFromVkWithoutDB(){
        List<Friend> friends = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        URL url1 = null;
        URL url2 = null;
        List<Group> groups = null;
        try {
            url1 = new URL("https://api.vk.com/method/friends.get?count=20&fields=city,country,sex,movies,music,occupation,relation,activities,bdate,career,activities,interests,music,movies,tv,books,games,about&v=5.100&access_token=e8d5e2366e9cca21f7c83ba93088e7118efd53f8a01f2576d94b3b8e6127b0d522c75a496eb4e8ff638b4");
            JsonNode jsonNode1 = objectMapper.readTree(url1);
            String str1 = jsonNode1.get("response").get("items").toString();
            friends = objectMapper.readValue(str1, new TypeReference<List<Friend>>(){});

            int user_id;
            JsonNode jsonNode2 = null;

            for (Friend friend : friends) {
                user_id = friend.getId();
                url2 = new URL(String.format("https://api.vk.com/method/users.getSubscriptions?fields=activity,description&user_id=%s&extended=1&v=5.100&access_token=e8d5e2366e9cca21f7c83ba93088e7118efd53f8a01f2576d94b3b8e6127b0d522c75a496eb4e8ff638b4", user_id));
                jsonNode2 = objectMapper.readTree(url2);
                String str2 = null;
                JsonNode jn = jsonNode2.get("response");
                if (jn != null) {
                    groups = objectMapper.convertValue(jn.get("items"), new TypeReference<List<Group>>() {
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
