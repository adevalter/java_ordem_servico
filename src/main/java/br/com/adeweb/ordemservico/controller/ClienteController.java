package br.com.adeweb.ordemservico.controller;

import br.com.adeweb.ordemservico.model.ClienteDTO;
import br.com.adeweb.ordemservico.service.ClienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> getAll(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "10") final Integer size
    ){
        return ResponseEntity.ok(clienteService.findAll(PageRequest.of(pageNumber,size)));
    }

    @GetMapping("/{id}")
    public ClienteDTO byId(@PathVariable Long id) {
        return  clienteService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO save(@RequestBody ClienteDTO clienteDTO){
        return clienteService.salvar(clienteDTO);
    }

    @PutMapping("/{id}")
    public ClienteDTO update(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO){
        ClienteDTO clienteAtual = clienteService.buscarPorId(id);
        BeanUtils.copyProperties(clienteDTO, clienteAtual, "id");
        return  clienteService.salvar(clienteAtual);
    }

}
