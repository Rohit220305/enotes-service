package com.example.demo.service;



import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.NotesDto;
import com.example.demo.dto.NotesResponse;
import com.example.demo.entity.FileDetails;
import com.example.demo.exception.ResourceNotFoundException;

public interface NotesService {
	
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public FileDetails getFileDetails(Integer id) throws Exception;

	public byte[] downloadFile(FileDetails fileDetails)throws Exception;

	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNO, Integer pageSize);

	public void softDeleteNotes(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception;

	public List<NotesDto> getUserRecycleBinNotes(Integer userId);

	public void hardDeleteNotes(Integer id) throws Exception;

	public void emptyRecycleBin(int userId);
	
	
}
