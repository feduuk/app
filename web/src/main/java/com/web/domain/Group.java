package com.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private int id;
    private String groupName;
    private String activity;
    private String screen_name;
}
