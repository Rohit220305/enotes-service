package com.example.demo.dto;

import com.example.demo.entity.Notes;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteNoteDto {

	private Integer id;
	
	private NotesDto note;

	private Integer userId;
}
