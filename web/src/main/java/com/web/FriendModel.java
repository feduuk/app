package com.web;

import lombok.Data;

import java.util.List;

@Data
public class FriendModel {
    private List<String> checkedActivities;

    public List<String> getCheckedActivities() {
        return checkedActivities;
    }
}
