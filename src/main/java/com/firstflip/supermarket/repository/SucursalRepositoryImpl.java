package com.firstflip.supermarket.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.firstflip.supermarket.model.Sucursal;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SucursalRepositoryImpl implements SucursalRepository {

  private final JdbcTemplate jdbcTemplate;

  @NonNull
  private final RowMapper<Sucursal> sucursalRowMapper = (ResultSet rs, int rows) -> {
    Sucursal sucursal = new Sucursal();
    sucursal.setId(rs.getLong("id"));
    sucursal.setNombre(rs.getString("nombre"));
    sucursal.setDireccion(rs.getString("direccion"));
    return sucursal;
  };

  @Override
  public List<Sucursal> findAll() {
    var sql = """
        select * from sucursal
        """;
    return jdbcTemplate.query(sql, sucursalRowMapper);
  }

  @Override
  public Optional<Sucursal> findById(@NonNull Long id) {
    try {
      var sql = """
          select * from sucursal where id=?
          """;
      Sucursal sucursal = jdbcTemplate.queryForObject(sql, sucursalRowMapper, id);
      return Optional.of(sucursal);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public boolean existsById(@NonNull Long id) {
    return findById(id).isPresent();
  }

  @Override
  public Sucursal save(@NonNull Sucursal sucursalDTO) {
    Sucursal sucursal = new Sucursal();
    sucursal.setNombre(sucursalDTO.getNombre());
    sucursal.setDireccion(sucursalDTO.getDireccion());
    if (sucursalDTO.getId() == null) {
      var sql = """
          insert into sucursal (nombre, direccion) values (?,?)
          """;
      GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
      PreparedStatementCreator psc = connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
        ps.setString(1, sucursal.getNombre());
        ps.setString(2, sucursal.getDireccion());
        return ps;
      };
      jdbcTemplate.update(psc, generatedKeyHolder);
      sucursal.setId(Objects.requireNonNull(generatedKeyHolder.getKey()).longValue());
    } else {
      sucursal.setId(sucursalDTO.getId());
      var sql = """
          update sucursal set nombre=?, direccion=? where id=?
          """;
      jdbcTemplate.update(sql, sucursal.getNombre(), sucursal.getDireccion(), sucursal.getId());
    }

    return sucursal;
  }

  @Override
  public void deleteById(@NonNull Long id) {
    var sql = """
        delete from sucursal where id=?
        """;
    jdbcTemplate.update(sql, id);
  }

}
