package com.example.demo.controller;

import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.FavouriteNoteDto;
import com.example.demo.dto.NotesDto;
import com.example.demo.dto.NotesResponse;
import com.example.demo.entity.FileDetails;
import com.example.demo.service.NotesService;
import com.example.demo.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> saveNotes(@RequestParam String notes , @RequestParam(required = false) MultipartFile file) throws Exception
	{
		Boolean saveNotes = notesService.saveNotes(notes,file);
		if (saveNotes) {
			 return CommonUtil.createBuildResponseMessage("notes saved successfully", HttpStatus.CREATED);
		}
		return CommonUtil.createErroeResponseMessage("notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception
	{	
		FileDetails fileDetails = notesService.getFileDetails(id);
		byte[] data = notesService.downloadFile(fileDetails);
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		
		
 		return ResponseEntity.ok().headers(headers).body(data);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllNotes()
	{
		List<NotesDto> notes = notesService.getAllNotes();
		if (org.springframework.util.CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@GetMapping("/user-notes")
	public ResponseEntity<?> getAllNotesByUser(
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNO,
			@RequestParam(name = "pageNo", defaultValue = "10") Integer pageSize)
	{
		Integer userId = 2;
		
		NotesResponse notes = notesService.getAllNotesByUser(userId ,pageNO, pageSize );
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception
	{
		notesService.restoreNotes(id);
		
		return CommonUtil.createBuildResponseMessage("Notes restore success", HttpStatus.OK);
	}
	
	@GetMapping("/recycle-bin")
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception
	{
		Integer userId = 2;
		List<NotesDto> notes=notesService.getUserRecycleBinNotes(userId);
		if(org.springframework.util.CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponseMessage("Notes not available in RecycleBin", HttpStatus.OK);
 
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception
	{
		notesService.softDeleteNotes(id);
		
		return CommonUtil.createBuildResponseMessage("Notes delete success", HttpStatus.OK);
	}


	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception
	{
		notesService.hardDeleteNotes(id);
		
		return CommonUtil.createBuildResponseMessage("Notes delete success", HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> emptyRecycleBin() throws Exception
	{
		int userId = 2;
	
		notesService.emptyRecycleBin(userId);
		
		return CommonUtil.createBuildResponseMessage("Notes delete success", HttpStatus.OK);
	}
	
	@GetMapping("/fav/{noteId}")
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception
	{
		
		notesService.favouriteNotes(noteId);
		
		return CommonUtil.createBuildResponseMessage("Notes added to Favourite", HttpStatus.OK);
	} 
	
	@DeleteMapping("/un-fav/{favNoteId}")
	public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNoteId) throws Exception
	{
		
		notesService.unFavouriteNotes(favNoteId);
		
		return CommonUtil.createBuildResponseMessage("Remove Favourite success", HttpStatus.OK);
	}
	
	@GetMapping("/fav-note")
	public ResponseEntity<?> getUserFavouriteNotes() throws Exception
	{
		int userId = 2;
	
		List<FavouriteNoteDto> userFavouriteNotes = notesService.getUserFavouriteNotes();
		
		if (org.springframework.util.CollectionUtils.isEmpty(userFavouriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtil.createBuildResponse(userFavouriteNotes, HttpStatus.OK);
	}
	
}
