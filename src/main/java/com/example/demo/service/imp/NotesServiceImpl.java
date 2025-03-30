package com.example.demo.service.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.config.ProjectConfig;
import com.example.demo.dto.CatagoryDto;
import com.example.demo.dto.NotesDto;
import com.example.demo.dto.NotesResponse;
import com.example.demo.entity.Catagory;
import com.example.demo.entity.FileDetails;
import com.example.demo.entity.Notes;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CatagoryRepository;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.NotesRepository;
import com.example.demo.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService{

    private final ProjectConfig projectConfig;

	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private FileRepository fileRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CatagoryRepository catagoryRepo;
	
	@Value("${file.upload.path}")
	private String uploadPath;

    NotesServiceImpl(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }
	
	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception  {
		
		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);
		
		// category validation notes
		checkcatagoryExist(notesDto.getCatagory());
		
		Notes notesMap = mapper.map(notesDto, Notes.class);
		
		FileDetails fileDetails = saveFileDetails(file);
		
		if (!ObjectUtils.isEmpty(fileDetails)) {
			notesMap.setFileDetails(fileDetails);
		}else {
			notesMap.setFileDetails(null);
		}
		
		Notes saveNotes = notesRepo.save(notesMap);
		if (!ObjectUtils.isEmpty(saveNotes)) {
			
			return true;
		}
		return false;
	}

	

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
		
		if(!ObjectUtils.isEmpty(file) &&  !file.isEmpty()) {
			String originalFileName = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFileName);
			
			List<String> extensionAllow = Arrays.asList("pdf","xlsx", "png","jpg");
			if(!extensionAllow.contains(extension)){
				throw new IllegalArgumentException("invalis file format !! upload only .pdf, .xlsx, .png, .jpg");
			}
			
			
			
//			String originalFileName = file.getOriginalFilename();
			
			
			String rndString = UUID.randomUUID().toString();
//			String extension = FilenameUtils.getExtension(originalFileName);
			String uploadFileName = rndString + "." +extension;
			
			
			
			File saveFile = new File(uploadPath);
			if(!saveFile.exists()) {
				saveFile.mkdir();
			}
			//path: C:\Project\enotes\enotes-service\notes\java.pdf
			String storePath = uploadPath.concat(uploadFileName);
			
			
			
			//upload file
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			if (upload!=0) {
				FileDetails fileDetails = new FileDetails();
				fileDetails.setOriginalFileName(originalFileName);
				fileDetails.setDisplayFileName(getDisplayName(originalFileName));
				fileDetails.setUploadFileName(uploadFileName);
				fileDetails.setFileSize(file.getSize());
				fileDetails.setPath(storePath);
				
				FileDetails saveFileDetails = fileRepo.save(fileDetails);
				return saveFileDetails;
			}
		}
		
		return null;
	}



	private String getDisplayName(String originalFileName) {
		// java_programming_tutorial.pdf
		
		String extension = FilenameUtils.getExtension(originalFileName);
		String fileName = FilenameUtils.removeExtension(originalFileName);
		
		if(fileName.length()>8) {
			fileName = fileName.substring(0, 7);
		}
		fileName = fileName+ "." + extension;
		return fileName;
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

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		
		InputStream io = new FileInputStream(fileDetails.getPath());
		
		return org.springframework.util.StreamUtils.copyToByteArray(io);
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		
		FileDetails fileDetails = fileRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("File is not available"));
		
		return fileDetails;
	}

	@Override
	public NotesResponse getAllNotesByUser(Integer userId,Integer pageNo, Integer pageSize) {
		
		//10 -> 5,5 - 2 pages
		Pageable pageable = PageRequest.of(pageNo, pageSize);
	 	Page<Notes> pageNotes = notesRepo.findByCreatedBy(userId,pageable);
	 	
	 	List<NotesDto> notesDto =pageNotes.get().map(n->mapper.map(n,NotesDto.class)).toList();
	 	
	 	NotesResponse notes = NotesResponse.builder()
	 			.notes(notesDto)
	 			.pageNo(pageNotes.getNumber())
	 			.pageSize(pageNotes.getSize())
	 			.totalElements(pageNotes.getTotalElements())
	 			.totalPages(pageNotes.getTotalPages())
	 			.isFirst(pageNotes.isFirst())
	 			.isLast(pageNotes.isLast())
	 			.build();
	 	
		return notes;
	}

}
