package com.e3mall.contentCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e3mall.contentCat.domain.TbContentCategory;

public interface ContentCategoryRepository extends JpaRepository<TbContentCategory, Long> {

	List<TbContentCategory> findAllByParentId(long id);

	int countByParentId(long parentId);

	TbContentCategory findOneById(Long parentId);

}
