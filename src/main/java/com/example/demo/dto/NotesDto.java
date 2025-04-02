package com.example.demo.dto;

import java.util.Date;

import com.example.demo.entity.Catagory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotesDto {
	
	private Integer id;
	
	private String tittle;
	
	private String description;
	
	private CatagoryDto catagory;
	
	private Integer createdBy;
	
	private Date createdOn;
	
	private Integer updatedBy;
	
	private Date updatedOn;
	
	private FilesDto fileDetails;
	
	private Boolean isDeleted;
	
	private Date deletedOn;
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class FilesDto{
		private Integer id;
		private String originalFileName;
		private String displayFileName;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class CatagoryDto{
		private Integer id;
		
		private String name;
	}

}
