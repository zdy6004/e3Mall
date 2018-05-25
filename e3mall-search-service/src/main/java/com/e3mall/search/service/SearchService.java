package com.e3mall.search.service;

import java.io.IOException;
import java.util.List;


import org.springframework.web.multipart.MultipartFile;

import com.e3mall.search.common.pojo.SearchResult;
import com.e3mall.search.common.utils.E3Result;

public interface SearchService {

	E3Result findItemList();

	SearchResult search(String keyword, int page, int rows);


}
