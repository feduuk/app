package com.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private int id;
    @JsonProperty("name")
    private String groupName;
    private String activity;
    private String screen_name;
}
