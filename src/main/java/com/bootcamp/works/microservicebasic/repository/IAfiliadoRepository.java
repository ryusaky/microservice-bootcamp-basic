package com.bootcamp.works.microservicebasic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.works.microservicebasic.bean.Afiliado;

public interface IAfiliadoRepository extends JpaRepository<Afiliado, Integer>{

	public boolean existsByNumerodocumento(String numeroDocumento);
	public boolean existsByNumerodocumentoAndAfpid(String numeroDocumento, short afpid);
	public Afiliado findByNumerodocumentoAndAfpid(String numeroDocumento, short afpid);
}
