package br.com.maddytec.marte.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.maddytec.marte.domain.Planalto;
import br.com.maddytec.marte.domain.Sonda;
import br.com.maddytec.marte.dto.SondaDTO;
import br.com.maddytec.marte.repository.PlanaltoRepository;
import br.com.maddytec.marte.repository.SondaRepository;
import br.com.maddytec.marte.utils.ComandoEnum;
import br.com.maddytec.marte.utils.DirecaoEnum;
import br.com.maddytec.marte.utils.ExploracaoEnum;

@Service
public class SondaService {

	@Autowired
	private SondaRepository sondaRepository;

	@Autowired
	private PlanaltoRepository planaltoRepository;

	@Autowired
	private PlanaltoService planaltoService;
	
	public Sonda save(SondaDTO sondaDTO) {

		List<Planalto> planaltos = planaltoRepository.findAll();

		if (!CollectionUtils.isEmpty(planaltos)) {
			Planalto planaltoEncontrado = planaltos.stream()
					.filter(planalto -> ExploracaoEnum.EXPLORACAO_SIM.getDescricao()
							.equals(planalto.getExploracao().getDescricao())							)
					.findAny().orElseThrow(() -> new IllegalStateException("Não existe um planalto em exploração para sonda explorar"));
			
			if(sondaDTO.getCoordenadaX() > planaltoEncontrado.getCoordenadaX()
					|| sondaDTO.getCoordenadaY() > planaltoEncontrado.getCoordenadaY()) {
				throw new IllegalStateException("Cordenada maior que os limites do planaldo.");
			}
			
		} else {
			throw new IllegalStateException("Não existe planalto");
		}

		Sonda sonda = new Sonda();
		BeanUtils.copyProperties(sondaDTO, sonda);

		return sondaRepository.save(sonda);
	}
	
	public Sonda findById(Long sondaId) {
		 return sondaRepository.findById(sondaId)
				 .orElseThrow(
						 () -> new IllegalStateException("Sonda não localizada"));
	}

	public Sonda explorar(Sonda sonda, String comando) {
		String novaDirecao = "";
		comando = comando.trim().toUpperCase();

		Planalto planaltoExploracao = planaltoService.buscarPlanaltoExploracao();
		if(planaltoExploracao != null 
			&& ExploracaoEnum.EXPLORACAO_SIM.getDescricao().equals(planaltoExploracao.getExploracao().getDescricao())) {
			for (int i = 0; i < comando.length(); i++) {
				novaDirecao = comando.substring(i, i + 1);
				mover(sonda, novaDirecao);
				alterarDirecao(sonda, novaDirecao);
			}
		} else {
			throw new IllegalStateException("Não há planalto com status para exploração");
		}
		
		return sonda;
	}

	private void mover(Sonda sonda, String executar) {
		Planalto planaltoEncontrado = planaltoService.buscarPlanaltoExploracao();
		if(ComandoEnum.M.name().equals(executar) && planaltoEncontrado != null) {
			sonda.setCoordenadaY(novaCordenadaPlanaltoExploracaoEixoY(sonda, planaltoEncontrado ));
		    sonda.setCoordenadaX(novaCordenadaPlanaltoExploracaoEixoX(sonda, planaltoEncontrado ));
		}
	}

	private Long novaCordenadaPlanaltoExploracaoEixoY(Sonda sonda, Planalto planaltoEncontrado) {
		if(sonda != null && planaltoEncontrado != null) {
			if(DirecaoEnum.N.name().equals(sonda.getDirecao().name())){
				if(!(sonda.getCoordenadaY() + 1 > planaltoEncontrado.getCoordenadaY())) {
					return sonda.getCoordenadaY() + 1;
				}
			}
					
			if(DirecaoEnum.S.name().equals(sonda.getDirecao().name())){
				if(!(sonda.getCoordenadaY() - 1 < 0)) {
					return sonda.getCoordenadaY() - 1;
				}
			}		
				
		}	
		return sonda.getCoordenadaY();	
	}

	
	private Long novaCordenadaPlanaltoExploracaoEixoX(Sonda sonda, Planalto planaltoEncontrado) {
		if(sonda != null && planaltoEncontrado != null) {
			if(DirecaoEnum.E.name().equals(sonda.getDirecao().name())){
				if(!(sonda.getCoordenadaX() + 1 > planaltoEncontrado.getCoordenadaX())) {
					return sonda.getCoordenadaX() + 1;
				}
			}
					
			if(DirecaoEnum.W.name().equals(sonda.getDirecao().name())){
				if(!(sonda.getCoordenadaX() - 1 < 0)) {
					return sonda.getCoordenadaX() - 1;
				}
			}		
				
		}	
		return sonda.getCoordenadaX();	
	}

	private void alterarDirecao(Sonda sonda, String novaDirecao) {
		if(!ComandoEnum.M.name().equals(novaDirecao)) {
		switch(sonda.getDirecao())
		{
		    case N:
	            sonda.setDirecao(DirecaoEnum.N.alterarDirecao(novaDirecao));
		            break;
		    case E:
		    	sonda.setDirecao(DirecaoEnum.E.alterarDirecao(novaDirecao));
	            break;
		    
		    case S:
		    	sonda.setDirecao(DirecaoEnum.S.alterarDirecao(novaDirecao));
	            break;
		    case W:
		    	sonda.setDirecao(DirecaoEnum.W.alterarDirecao(novaDirecao));
	            break;
			}
		}
	}

}
