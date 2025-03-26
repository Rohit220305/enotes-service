package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {
	
}
