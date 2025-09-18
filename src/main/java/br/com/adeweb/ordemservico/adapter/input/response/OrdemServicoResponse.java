package br.com.adeweb.ordemservico.adapter.input.response;

import br.com.adeweb.ordemservico.Enum.StatusOrdemServicoEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServicoResponse {
    private Long id;
    private ClienteResponse cliente;
    private String descricao;
    private StatusOrdemServicoEnum status;
    private BigDecimal valor;
    private LocalDateTime abertoEm;
    private LocalDateTime fechadoEm;
    private LocalDateTime atualizadoEm;
}
