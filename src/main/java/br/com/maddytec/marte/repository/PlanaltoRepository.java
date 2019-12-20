package br.com.maddytec.marte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.maddytec.marte.domain.Planalto;

@Repository
public interface PlanaltoRepository extends JpaRepository<Planalto, Long> {

}
