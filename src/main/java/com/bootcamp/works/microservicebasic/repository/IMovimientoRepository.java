package com.bootcamp.works.microservicebasic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.works.microservicebasic.bean.Movimiento;

public interface IMovimientoRepository extends JpaRepository<Movimiento,Integer>{
	
}
