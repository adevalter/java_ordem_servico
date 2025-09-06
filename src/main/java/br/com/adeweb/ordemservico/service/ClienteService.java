package br.com.adeweb.ordemservico.service;

import br.com.adeweb.ordemservico.entities.Cliente;
import br.com.adeweb.ordemservico.model.ClienteDTO;
import br.com.adeweb.ordemservico.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    public ClienteService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }
    public Page<ClienteDTO> findAll(Pageable pageable){
        Page<Cliente> clientes = clienteRepository.findAll(pageable);
        return clientes.map(cliente -> convertToDTO(cliente));
    }

    public ClienteDTO buscarPorId(Long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return convertToDTO(cliente);
    }

    public ClienteDTO salvar(ClienteDTO clienteDTO){
        Cliente cliente = convertToPessoa(clienteDTO);
        clienteRepository.save(cliente);
        return convertToDTO(cliente);
    }

    public ClienteDTO update(Long id, ClienteDTO clienteDTO){
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        // Copia os campos do DTO para a entidade existente
        clienteExistente.setNome(clienteDTO.getNome());
        clienteExistente.setEmail(clienteDTO.getEmail());

        clienteRepository.save(clienteExistente);
        return convertToDTO(clienteExistente);
    }

    private ClienteDTO convertToDTO(Cliente cliente){
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    private Cliente convertToPessoa(ClienteDTO clienteDTO){
        return modelMapper.map(clienteDTO, Cliente.class);
    }
}
