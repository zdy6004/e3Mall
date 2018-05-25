package com.e3mall.upload.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	String uploadPicture(MultipartFile uploadFile) throws IOException;

}
