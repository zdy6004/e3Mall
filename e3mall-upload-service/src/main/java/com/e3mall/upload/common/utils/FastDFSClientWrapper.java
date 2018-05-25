package com.e3mall.upload.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.MateData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

@Component
public class FastDFSClientWrapper {

	@Value("${PICTURE-SERVER.URL}")
	private String PICTURE_SERVER_URL;
	
    @Autowired
    private FastFileStorageClient storageClient;
    
   public String uploadFile(MultipartFile file) throws IOException {
       StorePath storePath = storageClient.uploadFile((InputStream)file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
       return getResAccessUrl(storePath);
   }
   
   // 封装文件完整URL地址
   private String getResAccessUrl(StorePath storePath) {
       String fileUrl = PICTURE_SERVER_URL + storePath.getFullPath();
       return fileUrl;
   }
}
