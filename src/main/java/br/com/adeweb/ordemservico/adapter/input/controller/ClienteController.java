package br.com.adeweb.ordemservico.adapter.input.controller;


import br.com.adeweb.ordemservico.adapter.input.mapper.ClienteMapper;
import br.com.adeweb.ordemservico.adapter.input.request.ClienteRequest;
import br.com.adeweb.ordemservico.adapter.input.response.ClienteResponse;
import br.com.adeweb.ordemservico.core.domain.model.Cliente;
import br.com.adeweb.ordemservico.core.usecase.ClienteUseCase;
import br.com.adeweb.ordemservico.port.input.ClienteInputPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cliente")
public class ClienteController {

    private final ClienteInputPort clienteInputPort;
    private final ClienteMapper clienteMapper;

    public ClienteController(ClienteUseCase clienteInputPort, ClienteMapper clienteMapper) {
        this.clienteInputPort = clienteInputPort;
        this.clienteMapper = clienteMapper;
    }

    @GetMapping
    public ResponseEntity<Page<ClienteRequest>> getAll(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "10") final Integer size
    ){
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<Cliente> clientes = clienteInputPort.findAll(pageable);

        Page<ClienteRequest> clienteRequests = clientes.map(clienteMapper::toRequest);

        return ResponseEntity.ok(clienteRequests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> byId(@PathVariable Long id) {
       Cliente cliente = clienteInputPort.buscarPorId(id);
       ClienteResponse clienteResponse = clienteMapper.toResponse(cliente);
       return ResponseEntity.ok(clienteResponse);
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> save(@RequestBody ClienteRequest clienteRequest){
        Cliente cliente = clienteMapper.toDomainFromRequest(clienteRequest);
        Cliente clienteSalvo = clienteInputPort.salvar(cliente);
        ClienteResponse response = clienteMapper.toResponse(clienteSalvo);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> update(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest){
        Cliente cliente = clienteMapper.toDomainFromRequest(clienteRequest);
        Cliente atualizado = clienteInputPort.update(id,cliente);
        ClienteResponse clienteResponse = clienteMapper.toResponse(atualizado);
        return ResponseEntity.ok(clienteResponse);
    }

}
