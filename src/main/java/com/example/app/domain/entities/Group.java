package com.example.app.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="group_vk")
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    private int id;
    @JsonProperty("name")
    private String groupName;
    private String activity;
    @Column(columnDefinition="text")
    private String description;
    @ManyToMany(
            mappedBy = "groups",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Friend> friends = new ArrayList<>();

}
