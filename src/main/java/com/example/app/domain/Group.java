package com.example.app.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity(name="group_vk")
@Table
public class Group {

    @Id
    @Column(name="id_of_group")
    private int id;
    private String nameOfGroup;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Friend> friendsVk = new ArrayList<>();
//    private String activity;
//    @Column(columnDefinition="text")
//    private String description;
//    @Transient
//    private Collection<Friend> friends = new ArrayList<>();


    public Group() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Collection<Friend> getFriends() {
//        return friends;
//    }
//
//    public void setFriends(Collection<Friend> friends) {
//        this.friends = friends;
//    }

    @JsonGetter("name")
    public String getNameOfGroup() {
        return nameOfGroup;
    }

    @JsonSetter("name")
    public void setNameOfGroup(String nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }

//    public String getActivity() {
//        return activity;
//    }
//
//    public void setActivity(String activity) {
//        this.activity = activity;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
}
