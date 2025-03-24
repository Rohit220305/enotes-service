package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Catagory;

public interface CatagoryRepository extends JpaRepository<Catagory, Integer> {

	List<Catagory> findByIsActiveTrueAndIsDeletedFalse();
	
	Optional<Catagory> findByIdAndIsDeletedFalse(Integer id);
	
	List<Catagory> findByIsDeletedFalse();

	Boolean existsByName(String name);

}
