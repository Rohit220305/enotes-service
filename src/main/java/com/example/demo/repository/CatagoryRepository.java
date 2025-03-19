package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Catagory;

public interface CatagoryRepository extends JpaRepository<Catagory, Integer> {

}
