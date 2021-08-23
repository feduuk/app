package com.example.app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend {

    @Id
    @Column
    private int id;
    private String first_name;
    private String last_name;
//    private int sex;
//    private String bdate;
//    @Column(columnDefinition="text")
//    private String interests;
//    @Column(columnDefinition="text")
//    private String books;
//    @Column(columnDefinition="text")
//    private String tv;
//    private String about;
//    @Column(columnDefinition="text")
//    private String games;
//    @Column(columnDefinition="text")
//    private String movies;
//    @Column(columnDefinition="text")
//    private String activities;
//    @Column(columnDefinition="text")
//    private String music;
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "friend_group",
            joinColumns = @JoinColumn(
                    name = "friendId",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "groupId",
                    referencedColumnName = "id"
            )
    )
    private List<Group> groups = new ArrayList<>();

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}