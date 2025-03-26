package com.example.demo.service;



import java.util.List;

import com.example.demo.dto.NotesDto;

public interface NotesService {
	
	public Boolean saveNotes(NotesDto notesDto) throws Exception;
	
	public List<NotesDto> getAllNotes();
	
	
}
