package com.bootcamp.works.microservicebasic.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "personas")
public class Persona {
	@Id
	@Column
	private String documentoidentidad;
	@Column
	private String nombres;
	@Column
	private String apellidos;
	@Column
	private String correo;
	@Column
	private String celular;
}
