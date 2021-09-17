package com.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(columnDefinition = "timestamptz")
    private Date updateDate;
}