package com.e3mall.itemCat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.itemCat.common.pojo.EasyUiTreeResult;
import com.e3mall.itemCat.domain.TbItemCat;
import com.e3mall.itemCat.repository.ItemCatRepository;


@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatRepository itemCatRepository;

	@Override
	public List<TbItemCat> findAllByParentId(long id) {
		
		return itemCatRepository.findAllByParentId(id);
	}
}
