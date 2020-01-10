package br.com.maddytec.marte.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.maddytec.marte.domain.Planalto;
import br.com.maddytec.marte.dto.PlanaltoDTO;
import br.com.maddytec.marte.repository.PlanaltoRepository;
import br.com.maddytec.marte.utils.ExploracaoEnum;

@Service
public class PlanaltoService {

	@Autowired
	private PlanaltoRepository planaltoRepository;

	public Planalto save(PlanaltoDTO planaltoDTO) {

		List<Planalto> planaltos = planaltoRepository.findAll();

		if (!CollectionUtils.isEmpty(planaltos)) {
			planaltos.stream()
					.filter(planalto -> ExploracaoEnum.EXPLORACAO_NAO.getDescricao()
							.equals(planalto.getExploracao().getDescricao()))
					.findAny().orElseThrow(() -> new IllegalStateException("Existe um planalto em exploração."));
		}

		Planalto planalto = new Planalto();
		BeanUtils.copyProperties(planaltoDTO, planalto);

		return planaltoRepository.save(planalto);
	}
	
	public Planalto finalizarExploração() {
		Planalto planaltoEncontrado = null;

		List<Planalto> planaltos = planaltoRepository.findAll();
		
		if (!CollectionUtils.isEmpty(planaltos)) {
			planaltoEncontrado = planaltos.stream()
					.filter(planalto -> ExploracaoEnum.EXPLORACAO_SIM.getDescricao()
							.equals(planalto.getExploracao().getDescricao()))
					.findAny().orElseThrow(null);
		}

		if(planaltoEncontrado != null) {
			planaltoEncontrado.setExploracao(ExploracaoEnum.EXPLORACAO_NAO);
		}
		
		return planaltoEncontrado;
	}
	
	public Planalto buscarPlanaltoExploracao() {
		List<Planalto> planaltos = planaltoRepository.findAll();
		if (!CollectionUtils.isEmpty(planaltos)) {
			return planaltos.stream().filter(planalto -> ExploracaoEnum.EXPLORACAO_SIM.getDescricao()
					.equals(planalto.getExploracao().getDescricao()))
					.findAny().orElse(null);
		}
		return null;
	}

}
