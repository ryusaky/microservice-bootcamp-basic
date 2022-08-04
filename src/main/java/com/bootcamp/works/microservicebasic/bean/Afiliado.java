package com.bootcamp.works.microservicebasic.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name="afiliados")
public class Afiliado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int afiliadoid;
	@Column
	private short afpid;
	@Column
	private double montodisponible;
	@Column
	private String numerodocumento;
}
