package com.bootcamp.works.microservicebasic.validators;

import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThanOrEqual;
import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

import com.bootcamp.works.microservicebasic.models.SolicitudModel;
import com.bootcamp.works.microservicebasic.util.Global;

import br.com.fluentvalidator.AbstractValidator;

public class ValidatorSolicitudRetiro extends AbstractValidator<SolicitudModel>{

	@Override
	public void rules() {
		// TODO Auto-generated method stub
		ruleFor(SolicitudModel::getPersona)
		.must(not(stringEmptyOrNull(p -> p.getDocumentoidentidad())))
		.withMessage("Debe especificar el documento de identidad")
		.withFieldName("documentoidentidad");
		
		ruleFor(SolicitudModel::getAfp)
			.must(not(nullValue(a->a.getAfpid())))
			.withMessage("Debe especificar la AFP a la que pertenece")
			.withFieldName("afpid");
		
		ruleFor(SolicitudModel::getMontoRetiro)
		.must(greaterThanOrEqual(Global.Solicitud.MONTO_MINIMO_RETIRO))
		.withMessage(s -> "Monto mínimo no cubierto por favor revise el monto mínimo a retirar")
		.withFieldName("montoAporte");
		
		ruleFor(SolicitudModel::getRucEmpresa)
		.must(not(stringEmptyOrNull()))
		.withMessage("Debe especificar el ruc de la empresa aportante")
		.withFieldName("rucEmpresa");
	}

}
