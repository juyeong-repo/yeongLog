package com.project.blog.dto;

import lombok.Data;

@Data
public class FileDto {
	  private Long		id;
	  private String	origFilename;
	  private String	filename;
	  private String	filePath;
	  private String	fileType;
	  private Long		fileSize;

}
