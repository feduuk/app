package com.example.storage.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="groupsVk")
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    private int id;
    @JsonProperty("name")
    @Column(columnDefinition = "text")
    private String groupName;
    @Column(columnDefinition = "text")
    private String activity;
    @JsonProperty("screen_name")
    private String screenName;
}
