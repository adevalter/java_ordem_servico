package br.com.adeweb.ordemservico.adapter.input.mapper;

import br.com.adeweb.ordemservico.adapter.input.request.OrdemServicoRequest;
import br.com.adeweb.ordemservico.adapter.input.response.OrdemServicoResponse;
import br.com.adeweb.ordemservico.adapter.output.entities.OrdemServicoEntity;
import br.com.adeweb.ordemservico.core.domain.model.OrdemServico;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface OrdemServicoMapper {

    OrdemServico toDaminFromRequest(OrdemServicoRequest ordemServicoRequest);
    OrdemServico toDomainFromEntity(OrdemServicoEntity ordemServicoEntity);
    OrdemServicoRequest toRequest(OrdemServico ordemServico);
    OrdemServicoResponse toResponse(OrdemServico ordemServico);
    List<OrdemServico> toDomainList(List<OrdemServicoEntity> ordemServicoEntityList);
}
