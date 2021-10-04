package com.web;

import java.util.Comparator;

public class ResponseComparator implements Comparator<Response> {
    @Override
    public int compare(Response o1, Response o2) {
        if(o1.getActivities().size() > o2.getActivities().size())
            return -1;
        else if(o1.getActivities().size() < o2.getActivities().size())
            return 1;
        else
            return 0;
    }
}
