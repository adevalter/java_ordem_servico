package br.com.adeweb.ordemservico.adapter.output.entities;

import br.com.adeweb.ordemservico.Enum.StatusOrdemServicoEnum;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdemServicoEntity {
    private Long id;
    private long clienteId;
    private String descricao;
    private StatusOrdemServicoEnum status;
    private BigDecimal valor;
    private LocalDateTime abertoEm;
    private LocalDateTime fechadoEm;
    private LocalDateTime atualizadoEm;
}


