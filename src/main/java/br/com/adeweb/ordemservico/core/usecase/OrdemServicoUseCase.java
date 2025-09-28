package br.com.adeweb.ordemservico.core.usecase;

import br.com.adeweb.ordemservico.core.domain.model.OrdemServico;
import br.com.adeweb.ordemservico.core.exception.EntidadeNaoEncontradaExecption;
import br.com.adeweb.ordemservico.port.input.OrdemServicoInputPort;
import br.com.adeweb.ordemservico.port.output.OrdemServicoOutputPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OrdemServicoUseCase implements OrdemServicoInputPort {
    private final OrdemServicoOutputPort outputPort;

    public OrdemServicoUseCase(OrdemServicoOutputPort outputPort) {
        this.outputPort = outputPort;
    }


    @Override
    public Page<OrdemServico> findAll(Pageable pageable) {
        return outputPort.findAll(pageable);
    }

    @Override
    public OrdemServico findById(Long id) {

        return outputPort.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaExecption("Ordem Serviço Não encontrado"));
    }

    @Override
    public OrdemServico save(OrdemServico ordemServico) {
        return outputPort.save(ordemServico);
    }

    @Override
    public OrdemServico update(Long id,OrdemServico ordemServico) {
        findById(id);
        return outputPort.update(id, ordemServico);
    }

    @Override
    public OrdemServico delete(OrdemServico ordemServico) {
        return outputPort.delete(ordemServico);
    }
}
