package br.com.adeweb.ordemservico.adapter.input.controller;

import br.com.adeweb.ordemservico.adapter.input.mapper.OrdemServicoMapper;
import br.com.adeweb.ordemservico.adapter.input.request.ClienteRequest;
import br.com.adeweb.ordemservico.adapter.input.request.OrdemServicoRequest;
import br.com.adeweb.ordemservico.adapter.input.response.ClienteResponse;
import br.com.adeweb.ordemservico.adapter.input.response.OrdemServicoResponse;
import br.com.adeweb.ordemservico.core.domain.model.OrdemServico;
import br.com.adeweb.ordemservico.port.input.OrdemServicoInputPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public ResponseEntity<Page<OrdemServicoResponse>> getAll(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "10") final Integer size
    ){
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<OrdemServico> ordemServicos = ordemServicoInputPort.findAll(pageable);
        Page<OrdemServicoResponse> ordemServicoResponses = ordemServicos.map(ordemServicoMapper::toResponse);
        return ResponseEntity.ok(ordemServicoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoResponse> byId(@PathVariable Long id) {
        OrdemServico ordemServico = ordemServicoInputPort.findById(id);
        return  ResponseEntity.ok(ordemServicoMapper.toResponse(ordemServico));
    }
    @PostMapping
    public ResponseEntity<OrdemServicoResponse> save(@RequestBody OrdemServicoRequest ordemServicoRequest){
        OrdemServico ordemServico = ordemServicoMapper.toDaminFromRequest(ordemServicoRequest);
        OrdemServico osSalva = ordemServicoInputPort.save(ordemServico);
        OrdemServicoResponse response = ordemServicoMapper.toResponse(osSalva);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemServicoResponse> update(@PathVariable Long id, @RequestBody OrdemServicoRequest ordemServicoRequest){
        OrdemServico ordemServico = ordemServicoMapper.toDaminFromRequest(ordemServicoRequest);
        OrdemServico osSalva = ordemServicoInputPort.update(id,ordemServico);
        OrdemServicoResponse response = ordemServicoMapper.toResponse(osSalva);
        return ResponseEntity.ok(response);
    }



}
