package com.example.week7challenge;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String dayorder;

    public String getDayorder() {
        return dayorder;
    }

    public void setDayorder(String name) {
        switch(name) {
            case "Monday": dayorder = "1"; break;
            case "Tuesday": dayorder = "2"; break;
            case "Wednesday": dayorder = "3"; break;
            case "Thursday": dayorder = "4"; break;
            case "Friday": dayorder = "5"; break;
            case "Saturday": dayorder = "6"; break;
            case "Sunday": dayorder = "7"; break;
            default: dayorder ="invalid";
        }
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fruit_id")
    private Fruit fruit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hour_id")
    private Hour hour;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public Hour getHour() {
        return hour;
    }

    public void setHour(Hour hour) {
        this.hour = hour;
    }
}