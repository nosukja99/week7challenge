package com.example.week7challenge;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Hour {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int startTime;
    private String amOrPm1;
    private String amOrPm2;
    private int endTime;

    private String schedule;

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(int startTime, String amOrPm1, String amOrPm2, int endTime)
    {
        if(startTime==endTime && amOrPm1.equals(amOrPm2))
        {

            schedule ="closed";
        }
        else {
            schedule = Integer.toString(startTime) + amOrPm1 + " ~ " + Integer.toString(endTime) + amOrPm2;
        }
    }

    @OneToMany(mappedBy = "hour", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    public Set<Day> days;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public String getAmOrPm1() {
        return amOrPm1;
    }

    public void setAmOrPm1(String amOrPm1) {
        this.amOrPm1 = amOrPm1;
    }

    public String getAmOrPm2() {
        return amOrPm2;
    }

    public void setAmOrPm2(String amOrPm2) {
        this.amOrPm2 = amOrPm2;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }
}
