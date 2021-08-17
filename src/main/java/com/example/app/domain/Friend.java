package com.example.app.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class Friend {

    @Id
    @Column(name="id_of_friend")
    private int id;
    private String first_name;
//    private String last_name;
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
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Group> groupsVK = new ArrayList<>();
//    @Transient
//    private Set<Group> groups = new HashSet<>();

    public Friend() {
    }

    public Friend(int id) {
        this.id = id;
    }

    public Friend(int id, String first_name, String last_name, int sex, String bdate, String interests, String books, String tv, String about, String games, String movies, String activities, String music) {
        this.id = id;
        this.first_name = first_name;
//        this.last_name = last_name;
//        this.sex = sex;
//        this.bdate = bdate;
//        this.interests = interests;
//        this.books = books;
//        this.tv = tv;
//        this.about = about;
//        this.games = games;
//        this.movies = movies;
//        this.activities = activities;
//        this.music = music;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

//    public String getLast_name() {
//        return last_name;
//    }
//
//    public void setLast_name(String last_name) {
//        this.last_name = last_name;
//    }
//
//    public int getSex() {
//        return sex;
//    }
//
//    public void setSex(int sex) {
//        this.sex = sex;
//    }
//
//    public String getBdate() {
//        return bdate;
//    }
//
//    public void setBdate(String bdate) {
//        this.bdate = bdate;
//    }
//
//    public String getInterests() {
//        return interests;
//    }
//
//    public void setInterests(String interests) {
//        this.interests = interests;
//    }
//
//    public String getBooks() {
//        return books;
//    }
//
//    public void setBooks(String books) {
//        this.books = books;
//    }
//
//    public String getTv() {
//        return tv;
//    }
//
//    public void setTv(String tv) {
//        this.tv = tv;
//    }
//
//    public String getAbout() {
//        return about;
//    }
//
//    public void setAbout(String about) {
//        this.about = about;
//    }
//
//    public String getGames() {
//        return games;
//    }
//
//    public void setGames(String games) {
//        this.games = games;
//    }
//
//    public String getMovies() {
//        return movies;
//    }
//
//    public void setMovies(String movies) {
//        this.movies = movies;
//    }
//
//    public String getActivities() {
//        return activities;
//    }
//
//    public void setActivities(String activities) {
//        this.activities = activities;
//    }
//
//    public String getMusic() {
//        return music;
//    }
//
//    public void setMusic(String music) {
//        this.music = music;
//    }

    public Collection<Group> getGroups() {
        return groupsVK;
    }

    public void setGroups(Collection<Group> groups) {
        this.groupsVK = groups;
    }
}