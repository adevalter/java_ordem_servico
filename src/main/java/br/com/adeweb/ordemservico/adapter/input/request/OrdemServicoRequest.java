package br.com.adeweb.ordemservico.adapter.input.request;

import br.com.adeweb.ordemservico.Enum.StatusOrdemServicoEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServicoRequest {
    private Long id;
    private long clienteId;
    private String descricao;
    private StatusOrdemServicoEnum status;
    private BigDecimal valor;
    private LocalDateTime abertoEm;
    private LocalDateTime fechadoEm;
    private LocalDateTime atualizadoEm;
}
