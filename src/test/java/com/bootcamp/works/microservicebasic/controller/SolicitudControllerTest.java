package com.bootcamp.works.microservicebasic.controller;


import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.bootcamp.works.microservicebasic.bean.AFP;
import com.bootcamp.works.microservicebasic.bean.Persona;
import com.bootcamp.works.microservicebasic.bean.Solicitud;
import com.bootcamp.works.microservicebasic.models.SolicitudModel;
import com.bootcamp.works.microservicebasic.repository.ISolicitudRepository;
import com.bootcamp.works.microservicebasic.util.Global;

@RunWith(MockitoJUnitRunner.class)
public class SolicitudControllerTest {

	@Mock
	ISolicitudRepository isolicituRepository;

	
	private SolicitudModel mockSolicitud;
	
	@InjectMocks
	SolicitudController solicitudController;
	
	
	@BeforeEach
	void setUp() {
		mockSolicitud = new SolicitudModel();
		mockSolicitud.setAfp(new AFP((short)1,"PRIMA","21328273819"));
		mockSolicitud.setMontoRetiro(2000);
		mockSolicitud.setTipoSolicitud(Global.TipoSolicitud.RETIRO);
		mockSolicitud.setPersona(new Persona("47528959", "LUIS", "PAZ RUMICHE", "pazrumicheluis@gmail.com", "983472563"));
		
	}
	
	
	@Test
	void testOk() {
		Solicitud responseSol = Solicitud.builder().solicitudid(1).tiposolicitud((short)3).fechasolicitud(new Date()).build();
		
		
	}
	
	

}
