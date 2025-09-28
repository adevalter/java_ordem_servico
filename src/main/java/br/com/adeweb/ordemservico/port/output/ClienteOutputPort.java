package br.com.adeweb.ordemservico.port.output;


import br.com.adeweb.ordemservico.core.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClienteOutputPort {
    Page<Cliente> findAll(Pageable pageable);
    Optional<Cliente> buscarPorId(Long id);
    Cliente salvar(Cliente cliente);
    Cliente update(Long id, Cliente cliente);
}
