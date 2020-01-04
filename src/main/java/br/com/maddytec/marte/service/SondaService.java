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
			planaltos.stream()
					.filter(planalto -> ExploracaoEnum.EXPLORACAO_SIM.getDescricao()
							.equals(planalto.getExploracao().getDescricao()))
					.findAny().orElseThrow(() -> new IllegalStateException("Não existe um planalto em exploração para sonda explorar"));
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
		String comandoEnviado = "";

		for (int i = 0; i < comando.length(); i++) {
			comandoEnviado = comando.substring(i, i + 1);
			mover(sonda, comandoEnviado);
			alterarDirecao(sonda, comandoEnviado);
		}
		return sonda;
	}

	private void mover(Sonda sonda, String comandoEnviado) {
		if(ComandoEnum.M.name().equals(comandoEnviado)) {
		switch(sonda.getDirecao() )
		{
		    case N:
		    	sonda.setCordenadaY(sonda.getCordenadaY() + 1);
		        break;
		    case E:
		    	sonda.setCordenadaX(sonda.getCordenadaX() + 1);
	            break;
		    
		    case S:
		    	sonda.setCordenadaY(sonda.getCordenadaY() - 1);
	            break;
		    case W:
		    	sonda.setCordenadaX(sonda.getCordenadaX() - 1);
	            break;
		    		    
			}
		}
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
