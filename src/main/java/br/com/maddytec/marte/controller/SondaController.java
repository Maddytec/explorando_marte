package br.com.maddytec.marte.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.maddytec.marte.domain.Sonda;
import br.com.maddytec.marte.dto.SondaDTO;
import br.com.maddytec.marte.repository.SondaRepository;
import br.com.maddytec.marte.service.SondaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Sonda")
@RestController
@RequestMapping(value = "sonda")
public class SondaController {

	@Autowired
	private SondaRepository sondaRepository;
	
	@Autowired
	private SondaService sondaService;

	@ApiOperation(value = "Salvar a posição inicial da sonda no planalto")
	@PostMapping
	public ResponseEntity<Sonda> save(@RequestBody @Valid SondaDTO sondaDTO) {

		Sonda sondaCriada = sondaService.save(sondaDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(sondaCriada);
	}
	
	
	@PostMapping(path = "/{sondaId}/{comandoExplorar}")
	public ResponseEntity<Sonda> explorar(
			@PathVariable(name = "sondaId") @Valid Long sondaId,
			@PathVariable(name = "comandoExplorar") String comandoExplorar){
		
		Sonda sonda = sondaService.findById(sondaId);
		
		   sonda = sondaService.explorar(sonda, comandoExplorar);
		
		//TODO exploração da sonda
		
		
		return ResponseEntity.status(HttpStatus.OK).body(sonda);
	}
	

	@ApiOperation(value = "Pesquisar as sondas")
	@GetMapping
	public ResponseEntity<List<Sonda>> findAll() {
		List<Sonda> sondas = sondaRepository.findAll();
		return ResponseEntity.ok(sondas);
	}
}
