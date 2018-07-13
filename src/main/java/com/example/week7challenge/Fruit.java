package com.example.week7challenge;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(min=1)
    private String name;

    private double price;

    private String image;

    @OneToMany(mappedBy = "fruit", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    public Set<Day> days;



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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }
}
