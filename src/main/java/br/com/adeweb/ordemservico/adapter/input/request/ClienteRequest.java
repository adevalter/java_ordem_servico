package br.com.adeweb.ordemservico.adapter.input.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    private Long id;
    private String nome;
    private String email;

}
