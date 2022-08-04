package com.bootcamp.works.microservicebasic.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.works.microservicebasic.bean.AFP;
import com.bootcamp.works.microservicebasic.models.ResponseMessage;
import com.bootcamp.works.microservicebasic.repository.IAFPRepository;
import com.bootcamp.works.microservicebasic.util.Global;
import com.bootcamp.works.microservicebasic.validators.ValidatorAFP;

import br.com.fluentvalidator.context.ValidationResult;
import io.swagger.annotations.Api;

@Api(value = "AFPController", description = "REST APIs relacionada a las gestión de AFP!!!!")
@RestController
@RequestMapping("/afp")
public class AFPController {

	ValidatorAFP _validatorAfp = new ValidatorAFP();

	@Autowired
	private IAFPRepository _iafpRepository;

	@GetMapping(value = "/all")
	public List<AFP> list() {
		return (List<AFP>) _iafpRepository.findAll();
	}

	@GetMapping(value = "/find/{afpid}")
	public ResponseMessage<AFP> find(@PathVariable short afpid) {
		if (_iafpRepository.existsById(afpid)) {
			return new ResponseMessage<AFP>(_iafpRepository.findById(afpid).get(), 0, "Consulta exitosa", null);
		}
		return new ResponseMessage<AFP>(new AFP(), Global.Errores.NOT_FOUND_MODEL,
				"No se encontró el modelo asociado al id ingresado", null);
	}

	@PostMapping(value = "/save")
	public ResponseMessage<AFP> save(@RequestBody AFP afp) {
		ValidationResult validation = _validatorAfp.validate(afp);
		if (validation.isValid()) {
			if (!_iafpRepository.existsByRuc(afp.getRuc())) {
				return new ResponseMessage<AFP>(_iafpRepository.save(afp), 0, "Afiliación exitosa", null);
			}
			return new ResponseMessage<AFP>(afp, Global.Errores.DUPLICATE_MODEL,
					"El Ruc asociado a la entidad, ya se encuentra registrado", null);
		}
		return new ResponseMessage<AFP>(null, Global.Errores.BUSINESS_LOGIC_VALIDATION, "",
				validation.getErrors().stream().map(e -> e.getMessage()).collect(Collectors.toList()));
	}

	@GetMapping(value = "/delete/{afpid}")
	public ResponseMessage<AFP> delete(@PathVariable short afpid) {
		if (_iafpRepository.existsById(afpid)) {
			AFP afp = _iafpRepository.getReferenceById(afpid);
			_iafpRepository.delete(afp);
			return new ResponseMessage<AFP>(new AFP(), 0, "Eliminación exitosa", null);
		}
		return new ResponseMessage<AFP>(new AFP(), Global.Errores.NOT_FOUND_MODEL,
				"No se encontró el modelo asociado al id ingresado", null);
	}

}
