package br.com.adeweb.ordemservico.repository;

import br.com.adeweb.ordemservico.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
