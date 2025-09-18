package br.com.adeweb.ordemservico.adapter.input.controller;

import br.com.adeweb.ordemservico.adapter.input.mapper.OrdemServicoMapper;
import br.com.adeweb.ordemservico.adapter.input.response.OrdemServicoResponse;
import br.com.adeweb.ordemservico.core.domain.model.OrdemServico;
import br.com.adeweb.ordemservico.port.input.OrdemServicoInputPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("ordemservico")
public class OrdemServicoController {
    private final OrdemServicoInputPort ordemServicoInputPort;
    private final OrdemServicoMapper ordemServicoMapper;

    public OrdemServicoController(OrdemServicoInputPort ordemServicoInputPort, OrdemServicoMapper ordemServicoMapper) {
        this.ordemServicoInputPort = ordemServicoInputPort;
        this.ordemServicoMapper = ordemServicoMapper;
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoResponse> byId(@PathVariable Long id) {
        Optional<OrdemServico> ordemServico = ordemServicoInputPort.findById(id);
        OrdemServicoResponse ordemServicoResponse = ordemServicoMapper.toResponse(ordemServico);
        return  ResponseEntity.ok(ordemServicoResponse);
    }
}
