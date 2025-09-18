package br.com.adeweb.ordemservico.adapter.output.repository.rowMapper;

import br.com.adeweb.ordemservico.adapter.output.entities.ClienteEntity;
import br.com.adeweb.ordemservico.utils.ConstantUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClienteRowMapper implements RowMapper<ClienteEntity> {

    @Override
    public ClienteEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(rs.getLong(ConstantUtils.ID));
        cliente.setNome(rs.getString(ConstantUtils.NOME));
        cliente.setEmail(rs.getString(ConstantUtils.EMAIL));
        return cliente;
    }
}
