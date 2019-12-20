package br.com.maddytec.marte.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.maddytec.marte.domain.Planalto;
import br.com.maddytec.marte.dto.PlanaltoDTO;
import br.com.maddytec.marte.repository.PlanaltoRepository;

@RestController
@RequestMapping(value = "planalto")
public class PlanaltoController {

	@Autowired
	PlanaltoRepository planaltoRepository;
	
	@PostMapping
	public ResponseEntity<Planalto> save(@RequestBody @Valid PlanaltoDTO planaltoDTO) {
		
		Planalto planalto = new Planalto();
		BeanUtils.copyProperties(planaltoDTO, planalto, "id");
		
		Planalto planaltoCriado = planaltoRepository.save(planalto);
		
		return ResponseEntity.status(HttpStatus.OK).body(planaltoCriado);
	}

	@GetMapping
	public ResponseEntity<List<Planalto>> buscar() {
		
		List<Planalto> planaltos = (List<Planalto>) planaltoRepository.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(planaltos);
	}
}
