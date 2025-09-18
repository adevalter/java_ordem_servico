package br.com.adeweb.ordemservico.core.domain.model;

import br.com.adeweb.ordemservico.Enum.StatusOrdemServicoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdemServico {
    private Long id;
    private Cliente cliente;
    private String descricao;
    private StatusOrdemServicoEnum status;
    private BigDecimal valor;
    private LocalDateTime abertoEm;
    private LocalDateTime fechadoEm;
    private LocalDateTime atualizadoEm;

    public OrdemServico() {
    }

    public OrdemServico(Long id, Cliente cliente, String descricao, StatusOrdemServicoEnum status, BigDecimal valor, LocalDateTime abertoEm, LocalDateTime fechadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.cliente = cliente;
        this.descricao = descricao;
        this.status = status;
        this.valor = valor;
        this.abertoEm = abertoEm;
        this.fechadoEm = fechadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusOrdemServicoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemServicoEnum status) {
        this.status = status;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getAbertoEm() {
        return abertoEm;
    }

    public void setAbertoEm(LocalDateTime abertoEm) {
        this.abertoEm = abertoEm;
    }

    public LocalDateTime getFechadoEm() {
        return fechadoEm;
    }

    public void setFechadoEm(LocalDateTime fechadoEm) {
        this.fechadoEm = fechadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
