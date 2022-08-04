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
@Table(name="afp" )
public class AFP {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private short afpid;
	@Column
	private String descripcion;
	@Column
	private String ruc;
}
