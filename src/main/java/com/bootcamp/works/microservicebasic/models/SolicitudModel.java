package com.bootcamp.works.microservicebasic.models;


import com.bootcamp.works.microservicebasic.bean.AFP;
import com.bootcamp.works.microservicebasic.bean.Persona;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudModel {
	
	private Persona persona;
	private AFP afp;
	private double montoRetiro;
	private double montoAporte;
	private short tipoSolicitud;
	private String rucEmpresa;
}
