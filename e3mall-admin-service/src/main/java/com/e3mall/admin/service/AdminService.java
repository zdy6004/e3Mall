package com.e3mall.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.e3mall.admin.common.pojo.DataGridResult;
import com.e3mall.admin.common.pojo.EasyUiTreeResult;
import com.e3mall.admin.common.utils.E3Result;
import com.e3mall.admin.domain.TbContent;
import com.e3mall.admin.domain.TbContentCategory;
import com.e3mall.admin.domain.TbItem;

public interface AdminService {
	List<EasyUiTreeResult> findCatList(long id);

	List<EasyUiTreeResult> findContentCategory(long id);

	E3Result save(TbContentCategory contentCategory);
	
	DataGridResult findItemList(int page, int row);

	E3Result save(TbItem item, String desc);

	E3Result deleteItemBatch(String ids);

	E3Result instockItemBatch(String ids);

	E3Result reshelfItemBatch(String ids);
	
	String uploadPicture(MultipartFile uploadFile);

	E3Result update(Long id, String name);

	E3Result delete(Long id);

	E3Result update(TbItem item);

	E3Result saveContent(TbContent content);

	DataGridResult findContentList(int page, int rows, long categoryId);

	E3Result editContent(TbContent content);

	E3Result deleteContent(String ids);

	E3Result importIndex();

}
