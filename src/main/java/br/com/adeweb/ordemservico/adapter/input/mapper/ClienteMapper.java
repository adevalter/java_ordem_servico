package br.com.adeweb.ordemservico.adapter.input.mapper;

import br.com.adeweb.ordemservico.adapter.input.request.ClienteRequest;
import br.com.adeweb.ordemservico.adapter.input.request.ClienteResponse;
import br.com.adeweb.ordemservico.adapter.output.entities.ClienteEntity;
import br.com.adeweb.ordemservico.core.domain.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    //Converte um DTO de entrada (ClienteRequest) para o objeto de domínio
    Cliente toDmain(ClienteRequest clienteRequest);
    //Converte uma entidade do banco (ClienteEntity) em objeto de domínio.
    Cliente toDomainEntity(ClienteEntity clienteEntity);
    //ConsultaEntity toEntity(Cliente consulta);
    ClienteEntity toEntity(Cliente cliente);
    //Converte do domínio para o DTO de request (pode ser usado em casos de reaproveitamento de dados).
    ClienteRequest toRequest(Cliente cliente);

    ClienteResponse toResponse(Cliente cliente);

}
