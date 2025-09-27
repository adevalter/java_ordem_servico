package br.com.adeweb.ordemservico.adapter.input.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nome;
    private String email;
  //  private final List<OrdemServicoResponse> ordensServico = new ArrayList<>();
}
