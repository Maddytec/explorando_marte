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
		String executar = "";
		comando = comando.trim().toUpperCase();

		Planalto planaltoExploracao = buscarPlanaltoExploracao();
		if(planaltoExploracao != null 
			&& ExploracaoEnum.EXPLORACAO_SIM.getDescricao().equals(planaltoExploracao.getExploracao().getDescricao())) {
			for (int i = 0; i < comando.length(); i++) {
				executar = comando.substring(i, i + 1);
				mover(sonda, executar);
				alterarDirecao(sonda, executar);
			}
		} else {
			throw new IllegalStateException("Não há planalto com status para exploração");
		}
		
		return sonda;
	}

	private void mover(Sonda sonda, String executar) {
		Planalto planaltoEncontrado = buscarPlanaltoExploracao();
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

	private Planalto buscarPlanaltoExploracao() {
		List<Planalto> planaltos = planaltoRepository.findAll();
		if (!CollectionUtils.isEmpty(planaltos)) {
			return planaltos.stream().filter(planalto -> ExploracaoEnum.EXPLORACAO_SIM.getDescricao()
					.equals(planalto.getExploracao().getDescricao()))
					.findAny().orElse(null);
		}
		return null;
	}
	
	private void alterarDirecao(Sonda sonda, String comandoEnviado) {
		if(!ComandoEnum.M.name().equals(comandoEnviado)) {
		switch(sonda.getDirecao() )
		{
		    case N:
		            if(comandoEnviado.equals(ComandoEnum.L.name())) {
		            	sonda.setDirecao(DirecaoEnum.W);
		            } else if(comandoEnviado.equals(ComandoEnum.R.name()))
		            	sonda.setDirecao(DirecaoEnum.E);
		            break;
		    case E:
		    	if(comandoEnviado.equals(ComandoEnum.L.name())) {
	            	sonda.setDirecao(DirecaoEnum.N);
	            } else if(comandoEnviado.equals(ComandoEnum.R.name()))
	            	sonda.setDirecao(DirecaoEnum.S);
	            break;
		    
		    case S:
		    	if(comandoEnviado.equals(ComandoEnum.L.name())) {
	            	sonda.setDirecao(DirecaoEnum.E);
	            } else if(comandoEnviado.equals(ComandoEnum.R.name()))
	            	sonda.setDirecao(DirecaoEnum.W);
	            break;
		    case W:
		    	if(comandoEnviado.equals(ComandoEnum.L.name())) {
	            	sonda.setDirecao(DirecaoEnum.S);
	            } else if(comandoEnviado.equals(ComandoEnum.R.name()))
	            	sonda.setDirecao(DirecaoEnum.N);
	            break;
		    		    
			}
		}
	}

}
