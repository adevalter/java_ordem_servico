package br.com.adeweb.ordemservico.adapter.output.repository;

import br.com.adeweb.ordemservico.adapter.input.mapper.ClienteMapper;
import br.com.adeweb.ordemservico.adapter.output.entities.ClienteEntity;
import br.com.adeweb.ordemservico.adapter.output.repository.rowMapper.ClienteRowMapper;
import br.com.adeweb.ordemservico.core.domain.model.Cliente;
import br.com.adeweb.ordemservico.core.exception.ClienteException;
import br.com.adeweb.ordemservico.port.output.ClienteOutputPort;
import br.com.adeweb.ordemservico.utils.ConstantUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ClienteRepository implements ClienteOutputPort {

    private final JdbcTemplate jdbcTemplate;
    private final ClienteMapper clienteMapper;
    private final ClienteRowMapper clienteRowMapper;

    public ClienteRepository(JdbcTemplate jdbcTemplate, ClienteMapper clienteMapper, ClienteRowMapper clienteRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.clienteMapper = clienteMapper;

        this.clienteRowMapper = clienteRowMapper;
    }

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        try {
            String sql = "SELECT id, nome, email FROM cliente LIMIT ? OFFSET ?";
            List<ClienteEntity> clienteEntities = jdbcTemplate.<ClienteEntity>query(
                    sql, clienteRowMapper,
                    pageable.getPageSize(), pageable.getOffset()
            );
            // total de registros
            String countSql = "SELECT COUNT(*) FROM cliente";
            Long total = jdbcTemplate.queryForObject(countSql, Long.class);
            return new PageImpl<>(clienteMapper.toDomainList(clienteEntities), pageable, total);
        } catch (DataAccessException e) {
            throw new ClienteException("Erro ao buscar clientes: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {

        String sql = "SELECT id, nome, email FROM cliente WHERE id = ?";
        try {
            ClienteEntity clienteEntity = jdbcTemplate.<ClienteEntity>queryForObject(sql, clienteRowMapper, id);
            return Optional.of(clienteMapper.toDomainEntity(clienteEntity));
        } catch (ClienteException e) {
            return Optional.empty();
        }

    }

    @Override
    public Cliente salvar(Cliente cliente) {
        try {
            ClienteEntity clienteEntity = clienteMapper.toEntity(cliente);
            String sql = "call pr_create_user(?,?,?) ";
            Long idGerado = jdbcTemplate.execute(connection -> {
                CallableStatement cs = connection.prepareCall(sql);

                if (cliente.getId() != null) {
                    cs.setLong(1, cliente.getId());
                } else {
                    cs.setNull(1, Types.BIGINT);
                }
                cs.registerOutParameter(1, Types.BIGINT);

                cs.setString(2, clienteEntity.getNome());
                cs.setString(3, clienteEntity.getEmail());
                return cs;
            }, (CallableStatement cs) -> {
                cs.execute();
                return cs.getLong(1);  // retorna o INOUT
            });
            clienteEntity.setId(idGerado);
            return clienteMapper.toDomainEntity(clienteEntity);

        } catch (DataAccessException e) {
            throw new ClienteException("Erro ao inserir usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public Cliente update(Long id, Cliente cliente) throws RuntimeException {


        try {
            cliente.setId(id);
            return this.salvar(cliente);
        } catch (DataAccessException ex) {
            throw new ClienteException("Erro ao atualizar Ordem Servico: " + id, ex);
        }

    }


}
