package br.com.adeweb.ordemservico.port.output;

import br.com.adeweb.ordemservico.core.domain.model.OrdemServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrdemServicoOutputPort {
    Page<OrdemServico> findAll(Pageable pageable);
    Optional<OrdemServico> findById(Long id);
    OrdemServico save(OrdemServico ordemServico);
    OrdemServico update(Long id, OrdemServico ordemServico);
    OrdemServico delete(OrdemServico ordemServico);
}
