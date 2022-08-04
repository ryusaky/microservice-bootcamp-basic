package com.bootcamp.works.microservicebasic.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.works.microservicebasic.bean.Afiliado;
import com.bootcamp.works.microservicebasic.bean.Movimiento;
import com.bootcamp.works.microservicebasic.bean.Persona;
import com.bootcamp.works.microservicebasic.bean.Solicitud;
import com.bootcamp.works.microservicebasic.models.ResponseMessage;
import com.bootcamp.works.microservicebasic.models.SolicitudModel;
import com.bootcamp.works.microservicebasic.repository.IAfiliadoRepository;
import com.bootcamp.works.microservicebasic.repository.IMovimientoRepository;
import com.bootcamp.works.microservicebasic.repository.IPersonaRepository;
import com.bootcamp.works.microservicebasic.repository.ISolicitudRepository;
import com.bootcamp.works.microservicebasic.util.Global;
import com.bootcamp.works.microservicebasic.validators.ValidatorRegistroAfiliacion;
import com.bootcamp.works.microservicebasic.validators.ValidatorSolicitudAporte;
import com.bootcamp.works.microservicebasic.validators.ValidatorSolicitudRetiro;

import br.com.fluentvalidator.context.ValidationResult;
import io.swagger.annotations.Api;

@Api(value = "SolicitudController", description = "REST APIs relacionada a las solicitudes de AFP!!!!")
@RestController
@RequestMapping("/solicitud")
public class SolicitudController {

	private ValidatorRegistroAfiliacion _validatorAfiliacion = new ValidatorRegistroAfiliacion();
	private ValidatorSolicitudAporte _validatorAporte = new ValidatorSolicitudAporte();
	private ValidatorSolicitudRetiro _validatorRetiro = new ValidatorSolicitudRetiro();

	@Autowired
	private ISolicitudRepository _isolicituRepository;

	@Autowired
	private IPersonaRepository _ipersonaRepository;

	@Autowired
	private IAfiliadoRepository _iafiliadoRepository;

	@Autowired
	private IMovimientoRepository _imovimientoRepository;

	@GetMapping
	public List<Solicitud> solicitudes() {
		return (List<Solicitud>) _isolicituRepository.findAll();
	}

	@PostMapping
	public ResponseMessage<SolicitudModel> insertar(@RequestBody SolicitudModel sol) {
		ValidationResult validation;
		
		switch (sol.getTipoSolicitud()) {
		case Global.TipoSolicitud.AFILIACION:
			validation = _validatorAfiliacion.validate(sol);
			if (validation.isValid()) {
				Persona per = new Persona();
				if (!_ipersonaRepository.existsById(sol.getPersona().getDocumentoidentidad())) {
					per = _ipersonaRepository.save(sol.getPersona());
				} else {
					per = _ipersonaRepository.findById(sol.getPersona().getDocumentoidentidad()).get();
				}

				if (!_iafiliadoRepository.existsByNumerodocumento(per.getDocumentoidentidad())) {
					Afiliado afiliado = _iafiliadoRepository
							.save(new Afiliado(0, sol.getAfp().getAfpid(), 0, per.getDocumentoidentidad()));
					Solicitud solicitud = _isolicituRepository
							.save(new Solicitud(0, sol.getTipoSolicitud(), new Date(), afiliado.getAfiliadoid(),null));
					_imovimientoRepository.save(new Movimiento(0,new Date(),solicitud.getTiposolicitud(),sol.getMontoAporte(),solicitud.getSolicitudid()));
					return new ResponseMessage<SolicitudModel>(new SolicitudModel(), 0, "Afiliación exitosa", null);
				}
				return new ResponseMessage<SolicitudModel>(new SolicitudModel(), Global.Errores.DUPLICATE_MODEL,
						"La persona ya se encuentra Afiliada a una AFP", null);
			}
			return new ResponseMessage<SolicitudModel>(null, Global.Errores.BUSINESS_LOGIC_VALIDATION, "",
					validation.getErrors().stream().map(e -> e.getMessage()).collect(Collectors.toList()));
		case Global.TipoSolicitud.APORTE:
			validation = _validatorAporte.validate(sol);
			if (validation.isValid()) {
				if (_iafiliadoRepository.existsByNumerodocumentoAndAfpid(sol.getPersona().getDocumentoidentidad(),
						sol.getAfp().getAfpid())) {
					Afiliado afiliado = _iafiliadoRepository.findByNumerodocumentoAndAfpid(
							sol.getPersona().getDocumentoidentidad(), sol.getAfp().getAfpid());
					Solicitud solicitud = _isolicituRepository
							.save(new Solicitud(0, sol.getTipoSolicitud(), new Date(), afiliado.getAfiliadoid(),sol.getRucEmpresa()));
					_imovimientoRepository.save(new Movimiento(0,new Date(),solicitud.getTiposolicitud(),sol.getMontoAporte(),solicitud.getSolicitudid()));
					afiliado.setMontodisponible(afiliado.getMontodisponible()+sol.getMontoAporte());
					_iafiliadoRepository.save(afiliado);
					return new ResponseMessage<SolicitudModel>(new SolicitudModel(), 0, "Aporte exitoso", null);
				}
				return new ResponseMessage<SolicitudModel>(new SolicitudModel(), Global.Errores.BUSINESS_LOGIC_VALIDATION, "No se encontró afiliación para el documento de identidad y afp proporcionados", null);
			}
			return new ResponseMessage<SolicitudModel>(null, Global.Errores.BUSINESS_LOGIC_VALIDATION, "",
					validation.getErrors().stream().map(e -> e.getMessage()).collect(Collectors.toList()));

		case Global.TipoSolicitud.RETIRO:
			validation = _validatorRetiro.validate(sol);
			if (validation.isValid()) {
				if (_iafiliadoRepository.existsByNumerodocumentoAndAfpid(sol.getPersona().getDocumentoidentidad(),
						sol.getAfp().getAfpid())) {
					Afiliado afiliado = _iafiliadoRepository.findByNumerodocumentoAndAfpid(
							sol.getPersona().getDocumentoidentidad(), sol.getAfp().getAfpid());
					if(afiliado.getMontodisponible()>sol.getMontoRetiro()) {
						if(_isolicituRepository.countSolicitudesByMovimiento(sol.getPersona().getDocumentoidentidad(), Global.TipoSolicitud.RETIRO)==0) {
							Solicitud solicitud = _isolicituRepository
									.save(new Solicitud(0, sol.getTipoSolicitud(), new Date(), afiliado.getAfiliadoid(),sol.getRucEmpresa()));
							_imovimientoRepository.save(new Movimiento(0,new Date(),solicitud.getTiposolicitud(),sol.getMontoRetiro(),solicitud.getSolicitudid()));
							afiliado.setMontodisponible(afiliado.getMontodisponible()-sol.getMontoRetiro());
							_iafiliadoRepository.save(afiliado);
							return new ResponseMessage<SolicitudModel>(new SolicitudModel(), 0, "Retiro exitoso", null);
						}
						return new ResponseMessage<SolicitudModel>(new SolicitudModel(), Global.Errores.BUSINESS_LOGIC_VALIDATION, "El afiliado tiene registrada una solicitud de retiro", null);
					}
					return new ResponseMessage<SolicitudModel>(new SolicitudModel(), Global.Errores.BUSINESS_LOGIC_VALIDATION, "No se puede registrar la solicitud. Monto mayor que el permitido", null);
				}
				return new ResponseMessage<SolicitudModel>(new SolicitudModel(), Global.Errores.BUSINESS_LOGIC_VALIDATION, "No se encontró afiliación para el documento de identidad y afp proporcionados", null);
			}
			return new ResponseMessage<SolicitudModel>(null, Global.Errores.BUSINESS_LOGIC_VALIDATION, "",
					validation.getErrors().stream().map(e -> e.getMessage()).collect(Collectors.toList()));

		default:
			return new ResponseMessage<SolicitudModel>(new SolicitudModel(), Global.Errores.BUSINESS_LOGIC_VALIDATION,
					"no se encontró la operación que desea realizar", null);
		}

	}

}
