package br.com.adeweb.ordemservico.adapter.input.mapper;

import br.com.adeweb.ordemservico.adapter.input.request.ClienteRequest;
import br.com.adeweb.ordemservico.adapter.input.response.ClienteResponse;
import br.com.adeweb.ordemservico.adapter.output.entities.ClienteEntity;
import br.com.adeweb.ordemservico.core.domain.model.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {


    Cliente toDomainFromRequest(ClienteRequest clienteRequest);

    Cliente toDomainEntity(ClienteEntity clienteEntity);
    List<Cliente> toDomainList(List<ClienteEntity> entities);

    ClienteRequest toRequest(Cliente cliente);
    ClienteResponse toResponse(Cliente cliente);

}
