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

}
