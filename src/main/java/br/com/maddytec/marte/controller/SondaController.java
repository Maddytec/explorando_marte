package br.com.maddytec.marte.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.maddytec.marte.domain.Planalto;
import br.com.maddytec.marte.domain.Sonda;
import br.com.maddytec.marte.dto.SondaDTO;
import br.com.maddytec.marte.repository.SondaRepository;
import br.com.maddytec.marte.service.SondaService;

@RestController
@RequestMapping(value = "sonda")
public class SondaController {

	@Autowired
	private SondaRepository sondaRepository;
	
	@Autowired
	private SondaService sondaService;
	
	@PostMapping
	public ResponseEntity<Sonda> save(@RequestBody @Valid SondaDTO sondaDTO) {

		Sonda sondaCriada = sondaService.save(sondaDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(sondaCriada);
	}

	@GetMapping
	public ResponseEntity<List<Sonda>> findAll() {
		List<Sonda> sondas = sondaRepository.findAll();
		return ResponseEntity.ok(sondas);
	}
}
