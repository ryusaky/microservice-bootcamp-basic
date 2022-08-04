package com.bootcamp.works.microservicebasic.repository;


//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bootcamp.works.microservicebasic.bean.Solicitud;

@Repository
public interface ISolicitudRepository extends JpaRepository<Solicitud,Integer>{

	
	@Query(value= "SELECT COUNT(s.solicitudid) FROM bootcamp.afiliados a  "
			+ " INNER JOIN bootcamp.solicitudes s ON s.afiliadoid = a.afiliadoid "
			+ " WHERE a.numerodocumento = :nrodoc and s.tiposolicitud = :movimiento ", 
			nativeQuery = true)
	public int countSolicitudesByMovimiento(String nrodoc, short movimiento);
}
