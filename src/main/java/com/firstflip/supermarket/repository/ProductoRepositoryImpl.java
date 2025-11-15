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
import com.firstflip.supermarket.model.CategoriaProducto;
import com.firstflip.supermarket.model.Producto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductoRepositoryImpl implements ProductoRepository {

  private final JdbcTemplate jdbcTemplate;

  @NonNull
  private final RowMapper<Producto> productoRowMapper = (ResultSet rs, int rows) -> {
    Producto producto = new Producto();
    producto.setId(rs.getLong("id"));
    producto.setNombre(rs.getString("nombre"));
    String categoriaId = rs.getString("categoria_id");
    if (categoriaId != null) {
      producto.setCategoriaProducto(CategoriaProducto.valueOf(categoriaId));
    }
    producto.setStock(rs.getInt("stock"));
    producto.setPrecio(rs.getDouble("precio"));
    return producto;
  };

  @Override
  public List<Producto> findAll() {
    var sql = """
        select * from producto order by id
        """;
    return jdbcTemplate.query(sql, productoRowMapper);
  }

  @Override
  public Optional<Producto> findById(@NonNull Long id) {
    var sql = """
        select * from producto where id = ?
        """;
    try {
      return Optional.of(jdbcTemplate.queryForObject(sql, productoRowMapper, id));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Producto> findByNombre(@NonNull String nombre) {
    var sql = """
        select * from producto where nombre = ?
        """;
    try {
      return Optional.of(jdbcTemplate.queryForObject(sql, productoRowMapper, nombre));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public boolean existsById(@NonNull Long id) {
    return findById(id).isPresent();
  }

  @Override
  public Producto save(@NonNull Producto productoDTO) {
    Producto producto = new Producto();
    producto.setNombre(productoDTO.getNombre());
    producto.setCategoriaProducto(productoDTO.getCategoriaProducto());
    producto.setStock(productoDTO.getStock());
    producto.setPrecio(productoDTO.getPrecio());
    if (productoDTO.getId() == null) {
      var sql = """
          INSERT INTO PRODUCTO (NOMBRE, CATEGORIA_ID, STOCK ,PRECIO)
          VALUES (?, ?, ?, ?)
          """;
      GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
      PreparedStatementCreator preparedStatementCreator = connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
        ps.setString(1, producto.getNombre());
        ps.setString(2, producto.getCategoriaProducto().name());
        ps.setInt(3, producto.getStock());
        ps.setDouble(4, producto.getPrecio());
        return ps;
      };
      jdbcTemplate.update(preparedStatementCreator, keyHolder);
      producto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    } else {
      producto.setId(productoDTO.getId());
      var sql = """
          UPDATE PRODUCTO SET NOMBRE=?, CATEGORIA_ID=?, STOCK=?, PRECIO=? WHERE ID=?
          """;

      jdbcTemplate.update(sql, producto.getNombre(), producto.getCategoriaProducto().name(),
          producto.getStock(), producto.getPrecio(), producto.getId());

    }
    return producto;
  }

  @Override
  public void deleteById(@NonNull Long id) {
    var sql = """
        delete PRODUCTO WHERE ID=?
        """;
    jdbcTemplate.update(sql, id);
  }

}
