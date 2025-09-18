package br.com.adeweb.ordemservico.adapter.output.repository;

import br.com.adeweb.ordemservico.adapter.input.mapper.OrdemServicoMapper;
import br.com.adeweb.ordemservico.adapter.output.entities.OrdemServicoEntity;
import br.com.adeweb.ordemservico.adapter.output.repository.rowMapper.OrdemServicoRowMapper;
import br.com.adeweb.ordemservico.core.domain.model.OrdemServico;
import br.com.adeweb.ordemservico.port.output.OrdemServicoOutputPort;
import br.com.adeweb.ordemservico.utils.ConstantUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class OrdemServicoRepository  implements OrdemServicoOutputPort {

    private final JdbcTemplate jdbcTemplate;
    private final OrdemServicoMapper ordemServicoMapper;
    private final OrdemServicoRowMapper ordemServicoRowMapper;

    public OrdemServicoRepository(JdbcTemplate jdbcTemplate, OrdemServicoMapper ordemServicoMapper, OrdemServicoRowMapper ordemServicoRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.ordemServicoMapper = ordemServicoMapper;
        this.ordemServicoRowMapper = ordemServicoRowMapper;
    }


    @Override
    public Page<OrdemServico> findAll(Pageable pageable) {
        try {
            String sql = "SELECT * FROM ordem_servico WHERE LIMIT ? OFFSET ?";

            List<OrdemServicoEntity> ordemServicoEntities = jdbcTemplate.query(
                    sql, ordemServicoRowMapper,
                    pageable.getPageSize(), pageable.getOffset());
            String countSql = "SELECT COUNT(*) FROM ORDEMSERVICO";
            Long total = jdbcTemplate.queryForObject(countSql, Long.class);
            return new PageImpl<>(ordemServicoMapper.toDomainList(ordemServicoEntities),pageable,total);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Ordem Servico: " + e.getMessage(), e);

        }

    }

    @Override
    public Optional<OrdemServico> findById(Long id) {
        String sql = "SELECT * FROM ordem_servico where id = ?";
        try {
            OrdemServicoEntity ordemServicoEntity = jdbcTemplate.<OrdemServicoEntity>queryForObject(sql, ordemServicoRowMapper, id);
            return Optional.of(ordemServicoMapper.toDomainFromEntity(ordemServicoEntity));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public OrdemServico save(OrdemServico ordemServico) {
        String sql = "INSERT INTO ordem_servico (cliente_id, descricao, status, valor) VALUES( ?, ?, ?, ?); ";
        KeyHolder keyHolder= new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{ConstantUtils.ID});
            ps.setLong(1, ordemServico.getCliente().getId());
            ps.setString(2,ordemServico.getDescricao());
            ps.setString(3,ordemServico.getStatus().name());
            ps.setBigDecimal(4,ordemServico.getValor());
            return ps;
        },keyHolder);
        ordemServico.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return ordemServico;
    }

    @Override
    public OrdemServico update(Long id,OrdemServico ordemServico) {
        String sql = "UPDATE ordem_servico SET descricao = ?, status = ?, valor = ?, atualizado_em = NOW() WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, ordemServico.getDescricao(),ordemServico.getStatus().name());
        if (rowsAffected == 0) throw new RuntimeException("Ordem de Serviço com id " +id + " não encontrado");

        String select  = "select * from ordem_servico where id = ?";
        OrdemServicoEntity ordemServicoEntity = jdbcTemplate.queryForObject(select, ordemServicoRowMapper,id);
        return ordemServicoMapper.toDomainFromEntity(ordemServicoEntity);
    }

    @Override
    public OrdemServico delete(OrdemServico ordemServico) {
        return null;
    }
}
