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
			
			if(sondaDTO.getCordenadaX() > planaltoEncontrado.getCordenadaX()
					|| sondaDTO.getCordenadaY() > planaltoEncontrado.getCordenadaY()) {
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

		for (int i = 0; i < comando.length(); i++) {
			executar = comando.substring(i, i + 1);
			mover(sonda, executar);
			alterarDirecao(sonda, executar);
		}
		return sonda;
	}

	private void mover(Sonda sonda, String executar) {
		Planalto planaltoEncontrado = buscarPlanaltoExploracao();
		if(ComandoEnum.M.name().equals(executar) && planaltoEncontrado != null) {
			sonda.setCordenadaY(novaCordenadaPlanaltoExploracaoEixoY(sonda, planaltoEncontrado ));
		    sonda.setCordenadaX(novaCordenadaPlanaltoExploracaoEixoX(sonda, planaltoEncontrado ));
		}
	}

	private Long novaCordenadaPlanaltoExploracaoEixoY(Sonda sonda, Planalto planaltoEncontrado) {
		if(sonda != null && planaltoEncontrado != null) {
			if(DirecaoEnum.N.name().equals(sonda.getDirecao().name())){
				if(!(sonda.getCordenadaY() + 1 > planaltoEncontrado.getCordenadaY())) {
					return sonda.getCordenadaY() + 1;
				}
			}
					
			if(DirecaoEnum.S.name().equals(sonda.getDirecao().name())){
				if(!(sonda.getCordenadaY() - 1 < 0)) {
					return sonda.getCordenadaY() - 1;
				}
			}		
				
		}	
		return sonda.getCordenadaY();	
	}

	
	private Long novaCordenadaPlanaltoExploracaoEixoX(Sonda sonda, Planalto planaltoEncontrado) {
		if(sonda != null && planaltoEncontrado != null) {
			if(DirecaoEnum.E.name().equals(sonda.getDirecao().name())){
				if(!(sonda.getCordenadaX() + 1 > planaltoEncontrado.getCordenadaX())) {
					return sonda.getCordenadaX() + 1;
				}
			}
					
			if(DirecaoEnum.W.name().equals(sonda.getDirecao().name())){
				if(!(sonda.getCordenadaX() - 1 < 0)) {
					return sonda.getCordenadaX() - 1;
				}
			}		
				
		}	
		return sonda.getCordenadaX();	
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
