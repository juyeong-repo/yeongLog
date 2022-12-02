package com.project.blog.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Locale;



import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.blog.dto.FileDto;
import com.project.blog.exception.YeongLogException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilService {
	

   public static String getCurrentDateTime() {
       Date today = new Date();
       Locale currentLocale = new Locale("KOREAN", "KOREA");
       String pattern = "yyyyMMddHHmmss";
       SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
       return formatter.format(today);
   }

   

  public static String getCurrentYyyymmdd() {
      return getCurrentDateTime().substring(0, 8);
  }

  

    public static List<FileDto> fileUpload(List<MultipartFile> files) {
    	if(files.size() < 1) {
    		new FileNotFoundException("업로드 할 파일이 없습니다.");
    	} 
       
    	String toDay = getCurrentYyyymmdd();
    	List<FileDto> filsList = new ArrayList<FileDto>();
    	for( MultipartFile file : files ) {
			FileDto fileDto = new FileDto();
			try {	

	            if( fileTypeCheck(file) ) {
	          		String origFilename = file.getOriginalFilename();
	            	String filename 	= getCurrentDateTime()+"_"+ origFilename;	//파일명 생성 규칙(등록일시)	ex)		/20220808101101_test.jpeg
	                String contentType 	= file.getContentType();
	                long fileSize 		= file.getSize();
	                String savePath 	= "C:\\var\\local\\storage"; 
	                

	                if (!new File(savePath).exists()) {
	                    try{
	                        new File(savePath).mkdir();
	                    } catch(Exception e) {
	                        e.getStackTrace();
	                    }
	                }
	                

	                String filePath = savePath + File.separator + filename;
	                try {
						file.transferTo(new File( filePath ));
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
	                
	                fileDto.setOrigFilename(origFilename);
	                fileDto.setFilename(filename);
	                fileDto.setFileType(contentType);
	                fileDto.setFileSize(fileSize);
	                filsList.add(fileDto);
	                
	    	    } else {
	    	    	throw new YeongLogException("등록 가능한 이미지 형식이 아닙니다."); 
	    	    }				
			}catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return filsList;
    }     
    
    
   
    public static Boolean fileTypeCheck(MultipartFile file) {
        Tika tika = new Tika();

        String mimeType = "";
        
		try {
			mimeType = tika.detect(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        if( 
        		mimeType.equals("image/png") || mimeType.equals("image/gif") || 
        		mimeType.equals("image/jpg") || mimeType.equals("image/jpeg") 
        ) {
        	return true;
        }else {
        	return false;
        }
    }
    


}
