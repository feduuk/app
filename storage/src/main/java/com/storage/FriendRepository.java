package com.storage;

import com.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {
    @Query(
            value = "SELECT DISTINCT friend.id FROM friend JOIN friend_group ON friend.id = friend_group.friend_id JOIN groups_vk ON friend_group.group_id = groups_vk.id WHERE activity IN (:activities);",
            nativeQuery = true
    )
    List<Integer> findFriendsIdByGroupActivity(@Param("activities") List<String> activities);
    @Query(
            value = "SELECT id FROM friend;",
            nativeQuery = true
    )
    List<Integer> findAllId();
    Friend findById(int id);



}
