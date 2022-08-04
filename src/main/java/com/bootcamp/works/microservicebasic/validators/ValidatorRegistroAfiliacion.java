package com.bootcamp.works.microservicebasic.validators;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ComparablePredicate.lessThanOrEqual;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;

import com.bootcamp.works.microservicebasic.models.SolicitudModel;
import com.bootcamp.works.microservicebasic.util.Global;

import br.com.fluentvalidator.AbstractValidator;

public class ValidatorRegistroAfiliacion extends AbstractValidator<SolicitudModel>{

	
	@Override
	public void rules() {
		setPropertyOnContext("Solicitud de Afiliación");
		
		ruleFor(SolicitudModel::getPersona)
		.whenever(not(nullValue()))
		.withValidator(new ValidatorPersona());
		
		ruleFor(SolicitudModel::getAfp)
		.must(not(nullValue(a->a.getAfpid())))
		.withMessage("Debe especificar la AFP a la que pertenece")
		.withFieldName("afpid");
		
		ruleFor(SolicitudModel::getMontoRetiro)
		.must(lessThanOrEqual(Global.Solicitud.MONTO_MINIMO_RETIRO))
		.withMessage("El monto minimo para retirar es de " + String.format("%.2f", Global.Solicitud.MONTO_MINIMO_RETIRO))
		.withFieldName("montoRetiro");
		
		/*
		 * ruleFor(SolicitudModel::getTipoSolicitud) .must(lessThanOrEqual((short)5))
		 * .withMessage("Debe seleccionar un tipo de solicitud válida")
		 * .must(greaterThanOrEqual((short)0))
		 * .withMessage("Debe seleccionar un tipo de solicitud válida 2")
		 * .withFieldName("tipoSolicitud");
		 */
		
	}

}
