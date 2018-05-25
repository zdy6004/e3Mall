package com.e3mall.sso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e3mall.sso.domain.TbUser;

public interface UserRepository extends JpaRepository<TbUser, Long> {

	List<TbUser> findAllByUsername(String param);

	List<TbUser> findAllByPhone(String param);

	TbUser findOneByUsername(String username);

}
