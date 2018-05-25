package com.e3mall.upload.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.e3mall.upload.common.utils.FastDFSClientWrapper;

@Service
public class UploadServiceImpl implements UploadService {
	@Autowired
	private FastDFSClientWrapper dfsclient;
	@Override
	public String uploadPicture(MultipartFile uploadFile) throws IOException {
		String url = dfsclient.uploadFile(uploadFile);
		return url;
	}

}
