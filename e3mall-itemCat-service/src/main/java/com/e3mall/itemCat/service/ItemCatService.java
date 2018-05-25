package com.e3mall.itemCat.service;

import java.util.List;

import com.e3mall.itemCat.common.pojo.EasyUiTreeResult;
import com.e3mall.itemCat.domain.TbItemCat;


public interface ItemCatService {

	List<TbItemCat> findAllByParentId(long id);

}
