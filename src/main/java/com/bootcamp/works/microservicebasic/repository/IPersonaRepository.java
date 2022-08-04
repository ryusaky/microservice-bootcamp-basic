package com.bootcamp.works.microservicebasic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.works.microservicebasic.bean.Persona;

public interface IPersonaRepository extends JpaRepository<Persona,String>{

}
