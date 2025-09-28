package br.com.adeweb.ordemservico.utils;

public class ConstantUtils {
    public static final String EMAIL = "email";
    public static final String NOME = "nome";
    public static final String ID = "id";
    public static final String CLIENTE_ID = "cliente_id";
    public static final String DESCRICAO = "descricao";
    public static final String STATUS = "status";
    public static final String VALOR = "valor";
    public static final String ABERTO_EM = "aberto_em";
    public static final String FECHADO_EM = "fechado_em";
    public static final String ATUALIZADO_EM = "atualizado_em";
    public static final String SQL_ALL_ORDEM_SERVICO = "SELECT * FROM ordem_servico LIMIT ? OFFSET ?";
    public static final String SQL_SELECT_BY_ID_ORDEM_SERVICO = "SELECT * FROM ordem_servico where id = ?";
}
