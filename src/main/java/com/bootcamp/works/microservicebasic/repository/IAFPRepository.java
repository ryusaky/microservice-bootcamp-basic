package com.bootcamp.works.microservicebasic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.works.microservicebasic.bean.AFP;

public interface IAFPRepository extends JpaRepository<AFP,Short>{

	public boolean existsByRuc(String ruc);
}