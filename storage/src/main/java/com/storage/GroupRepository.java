package com.storage;

import com.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query(
            value = "SELECT DISTINCT activity FROM groups_vk ORDER BY activity;",
            nativeQuery = true
    )
    List<String> findActivities();
}
