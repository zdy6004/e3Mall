package com.e3mall.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e3mall.cart.domain.TbContent;

public interface contentRepository extends JpaRepository<TbContent, Long>{

}
