package com.e3mall.upload.controller;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.e3mall.upload.service.UploadService;


@RestController
public class UploadPictureController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@Autowired
	private UploadService uploadService;
	@RequestMapping(value="admin/rest/pic/upload", method = RequestMethod.POST)
	public String uploadPic(@RequestParam("uploadFile") MultipartFile uploadFile) throws IOException{
			String result = uploadService.uploadPicture(uploadFile);
			return result;
		
	}

	

}
