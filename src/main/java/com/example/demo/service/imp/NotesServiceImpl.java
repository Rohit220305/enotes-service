package com.example.demo.service.imp;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.demo.dto.CatagoryDto;
import com.example.demo.dto.NotesDto;
import com.example.demo.entity.Catagory;
import com.example.demo.entity.Notes;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CatagoryRepository;
import com.example.demo.repository.NotesRepository;
import com.example.demo.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService{

	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CatagoryRepository catagoryRepo;
	
	@Override
	public Boolean saveNotes(NotesDto notesDto) throws Exception  {
		
		// category validation notes
		checkcatagoryExist(notesDto.getCatagory());
		
		Notes notes = mapper.map(notesDto, Notes.class);
		Notes saveNotes = notesRepo.save(notes);
		if (!ObjectUtils.isEmpty(saveNotes)) {
			
			return true;
		}
		
		return false;
	}

	

	private void checkcatagoryExist(com.example.demo.dto.NotesDto.CatagoryDto catagory) throws Exception {
		 catagoryRepo.findById(catagory.getId())
		 .orElseThrow(()-> new ResourceNotFoundException("category id invalid") );
	}



	@Override
	public List<NotesDto> getAllNotes() {

		return notesRepo.findAll().stream()
				.map(note->mapper.map(note, NotesDto.class)).toList();
		
		
	}

}
