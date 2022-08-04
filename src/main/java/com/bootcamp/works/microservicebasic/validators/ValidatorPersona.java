package com.bootcamp.works.microservicebasic.validators;

import com.bootcamp.works.microservicebasic.bean.Persona;

import br.com.fluentvalidator.AbstractValidator;

import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static br.com.fluentvalidator.predicate.LogicalPredicate.not;

public class ValidatorPersona extends AbstractValidator<Persona> {

	@Override
	public void rules() {
		setPropertyOnContext("persona");

		ruleFor(Persona::getDocumentoidentidad)
			.must(not(stringEmptyOrNull()))
			.withMessage("El documento de Identidad no puede estar en blanco")
			.withFieldName("documento_identidad");

		ruleFor(Persona::getNombres)
			.must(not(stringEmptyOrNull()))
			.withMessage("Los nombres de la persona no puede estar en blanco")
			.withFieldName("nombres");

		ruleFor(Persona::getApellidos)
			.must(not(stringEmptyOrNull()))
			.withMessage("Los apellidos de la persona no puede estar en blanco")
			.withFieldName("apellidos");
		
		ruleFor(Persona::getCorreo)
			.must(not(stringEmptyOrNull()))
			.withMessage("El correo de la persona no puede estar en blanco")
			.withFieldName("correo");
		
		ruleFor(Persona::getCelular)
			.must(not(stringEmptyOrNull()))
			.withMessage("El celular de la persona no puede estar en blanco")
			.withFieldName("celular");
	}

}
