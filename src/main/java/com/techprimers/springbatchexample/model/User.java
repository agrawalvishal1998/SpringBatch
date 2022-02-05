package com.techprimers.springbatchexample.model;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class User {

    @Id  //Used to define the primary key for this entity
    private Integer id;
    private String name;
    private String dept;
    private Integer salary;

    public User(Integer id, String name, String dept, Integer salary) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }

    public User() {
    }
}
