package br.com.adeweb.ordemservico.adapter.output.repository;

import br.com.adeweb.ordemservico.adapter.input.mapper.ClienteMapper;
import br.com.adeweb.ordemservico.adapter.output.entities.ClienteEntity;
import br.com.adeweb.ordemservico.adapter.output.repository.rowMapper.ClienteRowMapper;
import br.com.adeweb.ordemservico.core.domain.model.Cliente;
import br.com.adeweb.ordemservico.port.output.ClienteOutputPort;
import br.com.adeweb.ordemservico.utils.ConstantUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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
                    sql,clienteRowMapper,
                    new Object[]{pageable.getPageSize(), pageable.getOffset()}
            );
            // total de registros
            String countSql = "SELECT COUNT(*) FROM cliente";
            Long total = jdbcTemplate.queryForObject(countSql, Long.class);
            return new PageImpl<>(clienteMapper.toDomainList(clienteEntities), pageable, total);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar clientes: " + e.getMessage(), e);
        }
    }

    @Override
    public Cliente buscarPorId(Long id) {

        return buscarOptionalPorId(id)  // Optional<Cliente>
                .orElseThrow(() -> new NoSuchElementException("Cliente com id " + id + " não encontrado"));

    }

    @Override
    public Cliente salvar(Cliente cliente) {
        try {

                String sql = "INSERT INTO cliente (nome,email) values (?,?) ";
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{ConstantUtils.ID});
                    ps.setString(1, cliente.getNome());
                    ps.setString(2, cliente.getEmail());
                    return ps;
                }, keyHolder);
                long generatedId;
            generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            cliente.setId(generatedId);

            return  cliente;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public Cliente update(Long id, Cliente cliente) throws RuntimeException {
        String sql = "UPDATE cliente SET nome = ?, email = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, cliente.getNome(), cliente.getEmail(), id);

        if (rowsAffected == 0) throw new RuntimeException("Cliente com id " + id + " não encontrado.");

        // Buscar o cliente atualizado
        String selectSql = "SELECT id, nome, email FROM cliente WHERE id = ?";
        ClienteEntity clienteEntity = jdbcTemplate.<ClienteEntity>queryForObject(selectSql, clienteRowMapper, id);
        return clienteMapper.toDomainEntity(clienteEntity);
    }

    private Optional<Cliente> buscarOptionalPorId(Long id) {
        String sql = "SELECT id, nome, email FROM cliente WHERE id = ?";
        try {
            ClienteEntity clienteEntity = jdbcTemplate.<ClienteEntity>queryForObject(sql, clienteRowMapper, id);
            return Optional.of(clienteMapper.toDomainEntity(clienteEntity));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
