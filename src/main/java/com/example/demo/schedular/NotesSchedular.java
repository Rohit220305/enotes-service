package com.example.demo.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Notes;
import com.example.demo.repository.NotesRepository;

@Component
public class NotesSchedular {
	
	@Autowired
	private NotesRepository notesRepo;
	
	@Scheduled(cron = "0 0 0 * * ?")
//	@Scheduled(cron = "* * * ? * *")

	public void deleteNotesSchedular()
	{
		//delete the after 7 days from recycle bin
		
		LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
		List<Notes> deletedNotes = notesRepo.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDate);
		
		notesRepo.deleteAll(deletedNotes);
	}
	
}
