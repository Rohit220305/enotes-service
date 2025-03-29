package com.example.demo.service;



import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.NotesDto;

public interface NotesService {
	
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();
	
	
}
