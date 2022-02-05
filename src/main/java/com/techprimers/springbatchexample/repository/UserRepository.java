package com.techprimers.springbatchexample.repository;

import com.techprimers.springbatchexample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {  //It will use User model and Integer is the datatype for the primary key

}
