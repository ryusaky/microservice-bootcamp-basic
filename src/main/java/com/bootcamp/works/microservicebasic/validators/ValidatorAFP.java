package com.bootcamp.works.microservicebasic.validators;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
//import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;

import com.bootcamp.works.microservicebasic.bean.AFP;

import br.com.fluentvalidator.AbstractValidator;

public class ValidatorAFP extends AbstractValidator<AFP> {

	@Override
	public void rules() {
		// TODO Auto-generated method stub
		setPropertyOnContext("afp");

//		ruleFor(AFP::getAfpid)
//			.must(not(nullValue()))
//			.withMessage("Debe especificar la AFP a la que pertenece")
//			.withFieldName("afpid");

		ruleFor(AFP::getDescripcion).must(not(stringEmptyOrNull()))
				.withMessage("La descripción de la AFP no puede estar en blanco").withFieldName("descripcion");

		ruleFor(AFP::getRuc).must(not(stringEmptyOrNull()))
				.withMessage("El campo Ruc de la entidad AFP no puede estar en blanco").withFieldName("ruc");

	}

}
