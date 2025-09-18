package br.com.adeweb.ordemservico.core.usecase;


import br.com.adeweb.ordemservico.core.domain.model.Cliente;
import br.com.adeweb.ordemservico.port.input.ClienteInputPort;
import br.com.adeweb.ordemservico.port.output.ClienteOutputPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteUseCase implements ClienteInputPort {

    private final ClienteOutputPort outputPort;

    public ClienteUseCase(ClienteOutputPort outputPort) {
        this.outputPort = outputPort;
    }


    public Page<Cliente> findAll(Pageable pageable){
        return outputPort.findAll(pageable);
    }

    public Cliente buscarPorId(Long id){
        return outputPort.buscarPorId(id);
    }

    public Cliente salvar(Cliente cliente){
        return outputPort.salvar(cliente);
    }

    public Cliente update(Long id, Cliente cliente){
       buscarPorId(id);
       return outputPort.update(id,cliente);
    }


}
