package com.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.domain.Friend;
import com.web.domain.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class WorkerService {
    @Autowired
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Value("${my.value.host.storage.link}")
    String storageHost;
    @Value("${my.value.vk.version}")
    String vkVersion;

    public Friend getFriend(int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        Friend friend = restTemplate.exchange(String.format("%s/storage/getFriend/{id}", storageHost), HttpMethod.GET, entity, Friend.class, id).getBody();
        return friend;
    }

    public String saveAllFriends(List<Friend> friends) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<List<Friend>> entity = new HttpEntity<>(friends, headers);
        String friendsFromPost = restTemplate.postForObject(String.format("%s/storage/saveAllFriends", storageHost), entity, String.class);
        return friendsFromPost;
    }

    public long getUpdatedTimeDiffForCurrentFriend(Friend friendInDb) {
        Date date = friendInDb.getUpdateDate();
        Date nowDate = new Date();
        long diffInMillies = nowDate.getTime() - date.getTime();
        return TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public List<Group> getGroupsFromVk(int id, String token) throws IOException {
        List<Group> groups = new ArrayList<>();
        int offset = 0;
        int remainder = 0;
        do {
            URL url2 = new URL(String.format("https://api.vk.com/method/users.getSubscriptions?offset=%d&count=200&fields=activity,description&user_id=%s&extended=1&v=%s&access_token=%s", offset, id, vkVersion, token));
            JsonNode jsonNode2 = objectMapper.readTree(url2);
            JsonNode jn = jsonNode2.get("response");
            if (jn != null) {
                remainder = jn.get("count").asInt() - offset;
                groups.addAll(objectMapper.convertValue(jn.get("items"), new TypeReference<List<Group>>() {
                }));
            }
            offset += 200;
        } while (remainder > 200);
        return groups;
    }

    public void saveDataFromVk(String token, boolean actuality) {
        List<Friend> friends;
        List<Friend> friendsForSaving = new ArrayList<>();
        try {
            URL url1 = new URL(String.format("https://api.vk.com/method/friends.get?fields=sex,bdate&v=%s&access_token=%s", vkVersion, token));
            JsonNode jsonNode1 = objectMapper.readTree(url1);
            String str1 = jsonNode1.get("response").get("items").toString();
            friends = objectMapper.readValue(str1, new TypeReference<List<Friend>>() {});
            if(actuality == true){
                for(Friend friend : friends){
                    friend.setGroups(getGroupsFromVk(friend.getId(), token));
                    friend.setUpdateDate(new Date());
                    friendsForSaving.add(friend);
                }
            }else {
                for(Friend friend : friends){
                    Friend friendInDb = getFriend(friend.getId());
                    if (friendInDb == null || getUpdatedTimeDiffForCurrentFriend(friendInDb) > 24) {
                        friend.setGroups(getGroupsFromVk(friend.getId(), token));
                        friend.setUpdateDate(new Date());
                        friendsForSaving.add(friend);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveAllFriends(friendsForSaving);
    }
}
