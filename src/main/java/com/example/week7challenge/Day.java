package com.example.week7challenge;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

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