package com.e3mall.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e3mall.item.domain.TbContent;

public interface contentRepository extends JpaRepository<TbContent, Long>{

}
