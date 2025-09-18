package br.com.adeweb.ordemservico.adapter.output.repository.rowMapper;

import br.com.adeweb.ordemservico.Enum.StatusOrdemServicoEnum;
import br.com.adeweb.ordemservico.adapter.output.entities.OrdemServicoEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
@Component
public class OrdemServicoRowMapper implements RowMapper<OrdemServicoEntity> {

    @Override
    public OrdemServicoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrdemServicoEntity os = new OrdemServicoEntity();
        os.setId(rs.getLong("os_id"));
        os.setClienteId(rs.getLong("os_cliente_id"));
        os.setDescricao(rs.getString("os_descricao"));

        String status = rs.getString("os_status");
        if (status != null && !status.isBlank()) {
            os.setStatus(StatusOrdemServicoEnum.valueOf(status.toUpperCase()));
        }

        os.setValor(rs.getBigDecimal("os_valor"));
        os.setAbertoEm(getLocalDateTime(rs, "os_aberto_em"));
        os.setFechadoEm(getLocalDateTime(rs, "os_fechado_em"));
        os.setAtualizadoEm(getLocalDateTime(rs, "os_atualizado_em"));

        return os;
    }

    private LocalDateTime getLocalDateTime(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
