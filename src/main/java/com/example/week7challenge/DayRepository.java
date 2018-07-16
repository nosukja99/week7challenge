package com.example.week7challenge;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DayRepository extends CrudRepository<Day, Long>{
    public List<Day> findAllByOrderByDayorder();
    public List<Day> findAllByHour(Hour hour);
    long countByName(String name);
}
