package com.e3mall.content.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.e3mall.content.domain.TbContent;

public interface ContentRepository extends JpaRepository<TbContent, Long> {

	//List<TbContent> findAllByCategoryId(long categoryId, Pageable pageable);

	long countByCategoryId(long categoryId);

	

	@Query(value = "select c from TbContent c where c.categoryId = :categoryId")
	Page<TbContent> findAll(Pageable pageable, @Param("categoryId") long categoryId);



	List<TbContent> findAllByCategoryId(long categoryId);

	

}
