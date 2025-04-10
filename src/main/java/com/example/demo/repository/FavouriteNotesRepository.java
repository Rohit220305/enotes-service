package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.FavouriteNote;

public interface FavouriteNotesRepository extends JpaRepository<FavouriteNote, Integer>{

	List<FavouriteNote> findByUserId(int userId);

}
