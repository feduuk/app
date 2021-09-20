package com.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    private int id;
    private String first_name;
    private String last_name;
    private List<Group> groups = new ArrayList<>();
    private Date updateDate;
}