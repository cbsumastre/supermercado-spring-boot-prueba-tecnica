package com.firstflip.supermarket.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.firstflip.supermarket.model.DetalleVenta;
import com.firstflip.supermarket.model.EnumEstadoVenta;
import com.firstflip.supermarket.model.Producto;
import com.firstflip.supermarket.model.Sucursal;
import com.firstflip.supermarket.model.Venta;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VentaRepositoryImpl implements VentaRepository {

  private final JdbcTemplate jdbcTemplate;

  @NonNull
  private final RowMapper<Venta> ventaRowMapper = (ResultSet rs, int rows) -> {
    Venta venta = new Venta();
    venta.setId(rs.getLong("id"));
    Timestamp fecha = rs.getTimestamp("fecha");
    if (fecha != null) {
      venta.setFecha(fecha.toLocalDateTime());
    }
    String estado = rs.getString("estado");
    if (estado != null) {
      venta.setEstado(EnumEstadoVenta.valueOf(estado));
    }
    venta.setTotal(rs.getDouble("total"));
    Long sucursalId = rs.getLong("sucursal_id");
    if (sucursalId != null) {
      Sucursal sucursal = new Sucursal();
      sucursal.setId(sucursalId);
      sucursal.setNombre(rs.getString("sucursal_nombre"));
      sucursal.setDireccion(rs.getString("sucursal_direccion"));
      venta.setSucursal(sucursal);
    }
    venta.setDetalle(new ArrayList<>());

    return venta;
  };


  @NonNull
  private final RowMapper<DetalleVenta> detalleVentaRowMapper = (ResultSet rs, int rows) -> {
    DetalleVenta detalleVenta = new DetalleVenta();
    detalleVenta.setId(rs.getLong("id"));
    detalleVenta.setVenta(null);
    detalleVenta.setProducto(createProducto(rs));
    detalleVenta.setCantidad(rs.getInt("cantidad"));
    detalleVenta.setPrecio(rs.getDouble("precio"));
    return detalleVenta;
  };



  @Override
  public List<Venta> findAll() {
    var sql = """
        select v.id, v.fecha, v.estado, v.total,
            s.id as sucursal_id, s.nombre as sucursal_nombre, s.direccion as sucursal_direccion
            from venta v
         join sucursal s on s.id=v.sucursal_id
         """;
    return jdbcTemplate.query(sql, ventaRowMapper).stream().map(venta -> {
      venta.setDetalle(findDetalles(venta));
      return venta;
    }).toList();
  }



  @Override
  public Optional<Venta> findById(@NonNull Long id) {
    try {
      var sql = """
          select v.id, v.fecha, v.estado, v.total,
              s.id as sucursal_id, s.nombre as sucursal_nombre, s.direccion as sucursal_direccion
              from venta v
           join sucursal s on s.id=v.sucursal_id
           where v.id=?
           """;
      Venta venta = jdbcTemplate.queryForObject(sql, ventaRowMapper, id);
      if (venta != null) {
        venta.setDetalle(findDetalles(venta));
        return Optional.of(venta);
      }
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
    return Optional.empty();
  }

  @Override
  public boolean existsById(@NonNull Long id) {
    try {
      var sql = """
          select v.id
              from venta v where v.id=?
           """;
      return jdbcTemplate.queryForObject(sql, Long.class, id) != null;
    } catch (EmptyResultDataAccessException e) {
    }
    return false;
  }

  @Override
  public Venta save(@NonNull Venta venta) {
    if (venta.getId() == null) {
      var insertVentaSql = """
          insert into venta (fecha, estado, sucursal_id, total)
          values (?,?,?,?)
          """;
      GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
      PreparedStatementCreator psc = connection -> {
        PreparedStatement ps = connection.prepareStatement(insertVentaSql, new String[] {"id"});
        if (venta.getFecha() != null) {
          ps.setTimestamp(1, Timestamp.valueOf(venta.getFecha()));
        }
        ps.setString(2, venta.getEstado().name());
        ps.setLong(3, venta.getSucursal().getId());
        ps.setDouble(4, venta.getTotal());
        return ps;
      };
      jdbcTemplate.update(psc, generatedKeyHolder);
      venta.setId(Objects.requireNonNull(generatedKeyHolder.getKey()).longValue());
      insertOrUpdateDetalles(venta);
    } else {
      insertOrUpdateDetalles(venta);
    }
    return venta;
  }

  private void insertOrUpdateDetalles(Venta venta) {
    Validate.notNull(venta.getId(), "Venta tiene id null");
    venta.getDetalle().forEach(detalle -> {
      insertOrUpdateDetalle(venta, detalle);
    });
  }



  private void insertOrUpdateDetalle(Venta venta, DetalleVenta detalle) {
    if (detalle.getId() == null) {
      var sql = """
          insert into detalle_venta (venta_id, producto_id, cantidad, precio)
          values (?, ?, ?, ?)
          """;
      GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
      PreparedStatementCreator psc = connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
        ps.setLong(1, venta.getId());
        ps.setLong(2, detalle.getProducto().getId());
        ps.setInt(3, detalle.getCantidad());
        ps.setDouble(4, detalle.getPrecio());
        return ps;
      };
      jdbcTemplate.update(psc, generatedKeyHolder);
      detalle.setId(Objects.requireNonNull(generatedKeyHolder.getKey()).longValue());
    } else {
      var sql = """
          update detalle_venta set venta_id=?, producto_id=?, cantidad=?, precio=?
          where id=?
          """;
      jdbcTemplate.update(sql, venta.getId(), detalle.getProducto().getId(), detalle.getCantidad(),
          detalle.getPrecio(), detalle.getId());
    }
  }



  @Override
  public void deleteById(@NonNull Long id) {
    var sqlBorrarDetalles = """
            delete from detalle_venta where venta_id=?
        """;
    jdbcTemplate.update(sqlBorrarDetalles, id);

    var sqlBorrarVenta = """
        delete from venta where id=?
        """;
    jdbcTemplate.update(sqlBorrarVenta, id);
  }

  private List<DetalleVenta> findDetalles(@NonNull Venta venta) {
    var sql =
        """
            select dv.id, dv.producto_id, dv.cantidad, dv.precio,
            p.nombre as producto_nombre, p.categoria_id as producto_categoria_id, p.stock as producto_stock, p.precio producto_precio
            from detalle_venta dv
            join producto p on p.id=dv.producto_id
            where venta_id=?
             """;
    return jdbcTemplate.query(sql, detalleVentaRowMapper, venta.getId()).stream().map(d -> {
      d.setVenta(venta);
      return d;
    }).toList();
  }

  private Producto createProducto(ResultSet rs) throws SQLException {
    Long productoId = rs.getLong("producto_id");
    String productoNombre = rs.getString("producto_nombre");
    String productoCategoriaId = rs.getString("producto_categoria_id");
    CategoriaProducto categoriaProducto = CategoriaProducto.valueOf(productoCategoriaId);
    int productoStock = rs.getInt("producto_stock");
    double productoPrecio = rs.getDouble("producto_precio");
    Producto producto = Producto.builder().id(productoId).nombre(productoNombre)
        .categoriaProducto(categoriaProducto).stock(productoStock).precio(productoPrecio).build();
    return producto;
  }

}
