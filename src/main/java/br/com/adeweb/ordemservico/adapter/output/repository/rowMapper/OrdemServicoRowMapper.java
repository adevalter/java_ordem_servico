package br.com.adeweb.ordemservico.adapter.output.repository.rowMapper;

import br.com.adeweb.ordemservico.Enum.StatusOrdemServicoEnum;
import br.com.adeweb.ordemservico.adapter.output.entities.OrdemServicoEntity;
import br.com.adeweb.ordemservico.utils.ConstantUtils;
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
        os.setId(rs.getLong(ConstantUtils.ID));
        os.setClienteId(rs.getLong(ConstantUtils.CLIENTE_ID));
        os.setDescricao(rs.getString(ConstantUtils.DESCRICAO));

        String status = rs.getString(ConstantUtils.STATUS);
        if (status != null && !status.isBlank()) {
            os.setStatus(StatusOrdemServicoEnum.valueOf(status.toUpperCase()));
        }

        os.setValor(rs.getBigDecimal(ConstantUtils.VALOR));
        os.setAbertoEm(getLocalDateTime(rs, ConstantUtils.ABERTO_EM));
        os.setFechadoEm(getLocalDateTime(rs, ConstantUtils.FECHADO_EM));
        os.setAtualizadoEm(getLocalDateTime(rs, ConstantUtils.ATUALIZADO_EM));

        return os;
    }

    private LocalDateTime getLocalDateTime(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
