package br.com.maddytec.marte.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.maddytec.marte.domain.Planalto;

@Repository
public interface PlanaltoRepository extends CrudRepository<Planalto, Long> {

}
