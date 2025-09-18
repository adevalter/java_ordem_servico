package br.com.adeweb.ordemservico.adapter.output.entities;


import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteEntity {
    private Long id;
    private String nome;
    private String email;

}