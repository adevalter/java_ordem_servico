package br.com.adeweb.ordemservico.adapter.output.repository;

import br.com.adeweb.ordemservico.adapter.input.mapper.OrdemServicoMapper;
import br.com.adeweb.ordemservico.adapter.output.entities.OrdemServicoEntity;
import br.com.adeweb.ordemservico.adapter.output.repository.rowMapper.OrdemServicoRowMapper;
import br.com.adeweb.ordemservico.core.domain.model.OrdemServico;
import br.com.adeweb.ordemservico.core.exception.OrdemServicoException;
import br.com.adeweb.ordemservico.port.output.OrdemServicoOutputPort;
import br.com.adeweb.ordemservico.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class OrdemServicoRepository implements OrdemServicoOutputPort {

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
            List<OrdemServicoEntity> ordemServicoEntities = jdbcTemplate.query(
                    ConstantUtils.SQL_ALL_ORDEM_SERVICO, ordemServicoRowMapper,
                    pageable.getPageSize(), pageable.getOffset());
            String countSql = "SELECT COUNT(*) FROM ordem_servico";
            Long total = jdbcTemplate.queryForObject(countSql, Long.class);
            return new PageImpl<>(ordemServicoMapper.toDomainList(ordemServicoEntities), pageable, total);
        } catch (DataAccessException e) {
            throw new OrdemServicoException("Erro ao buscar Ordem Servico: " + e.getMessage(), e);
        }

    }

    @Override
    public Optional<OrdemServico> findById(Long id) {

        try {
            OrdemServicoEntity ordemServicoEntity = jdbcTemplate.<OrdemServicoEntity>queryForObject(
                    ConstantUtils.SQL_SELECT_BY_ID_ORDEM_SERVICO, ordemServicoRowMapper, id);

            return Optional.of(ordemServicoMapper.toDomainFromEntity(ordemServicoEntity));

        } catch (DataAccessException e) {
            throw new OrdemServicoException("Codigo Não Existe : " + id, e);
        }
    }

    @Override
    public OrdemServico save(OrdemServico ordemServico) {
        String sql = "CALL pr_create_ordem_servico(?, ?, ?, ?, ?)";

        try {
            OrdemServicoEntity ordemServicoEntity = ordemServicoMapper.toEntity(ordemServico);
            Long idGerado = jdbcTemplate.execute(connection -> {
                CallableStatement cs = connection.prepareCall(sql);

                if (ordemServicoEntity.getId() != null) {
                    cs.setLong(1, ordemServicoEntity.getId());
                } else {
                    cs.setNull(1, Types.BIGINT);
                }
                cs.registerOutParameter(1, Types.BIGINT);

                cs.setLong(2, ordemServicoEntity.getClienteId());
                cs.setString(3, ordemServicoEntity.getDescricao());
                cs.setString(4, ordemServicoEntity.getStatus().name());
                cs.setBigDecimal(5, ordemServicoEntity.getValor());
                return cs;
            }, (CallableStatement cs) -> {
                cs.execute();
                return cs.getLong(1);  // retorna o INOUT
            });
            return findById(idGerado)
                    .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada após inserção"));
        } catch (DataAccessException ex) {
            log.warn("Erro ao executar procedure: " + ex.getMessage());
            throw new RuntimeException("Erro ao Cadastrar Ordem Servico: ", ex);
        }
    }

    @Override
    public OrdemServico update(Long id, OrdemServico ordemServico) {

        try {
            ordemServico.setId(id);
            return this.save(ordemServico);
        } catch (DataAccessException ex) {
            log.warn("Erro ao executar procedure: " + ex.getMessage());
            throw new OrdemServicoException("Erro ao atualizar Ordem Servico: " + id, null);
        }
    }

    @Override
    public OrdemServico delete(OrdemServico ordemServico) {
        return null;
    }
}
