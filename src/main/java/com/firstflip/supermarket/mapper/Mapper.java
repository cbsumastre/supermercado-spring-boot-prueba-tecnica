package com.firstflip.supermarket.mapper;

import com.firstflip.supermarket.dto.DetalleVentaDTO;
import com.firstflip.supermarket.dto.ProductoDTO;
import com.firstflip.supermarket.dto.SucursalDTO;
import com.firstflip.supermarket.dto.VentaDTO;
import com.firstflip.supermarket.model.DetalleVenta;
import com.firstflip.supermarket.model.Producto;
import com.firstflip.supermarket.model.Sucursal;
import com.firstflip.supermarket.model.Venta;

public class Mapper {

  // Mapeo de Producto a ProductoDTO
  public static ProductoDTO toDTO(Producto p) {
    if (p == null) {
      return null;
    }

    return ProductoDTO.builder().id(p.getId()).nombre(p.getNombre())
        .categoriaProducto(
            p.getCategoriaProducto() != null ? p.getCategoriaProducto().name() : null)
        .stock(p.getStock()).precio(p.getPrecio()).build();
  }

  // Mapeo de Sucursal a SucursalDTO
  public static SucursalDTO toDTO(Sucursal s) {
    if (s == null) {
      return null;
    }
    return SucursalDTO.builder().id(s.getId()).direccion(s.getDireccion()).nombre(s.getNombre())
        .build();
  }

  // Mapeo de Venta a VentaDTO
  public static VentaDTO toDTO(Venta v) {
    if (v == null) {
      return null;
    }

    var detalle = v.getDetalle().stream().map(Mapper::toDTO).toList();

    // var total = detalle.stream().map(DetalleVentaDTO::getSubtotal).filter(d -> d != null)
    // .map(d -> BigDecimal.valueOf(d)).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue();

    @SuppressWarnings("null")
    var total = detalle.stream().map(DetalleVentaDTO::getSubtotal).reduce(0.0, Double::sum);

    return VentaDTO.builder().id(v.getId()).fecha(v.getFecha()).estado(v.getEstado().name())
        .total(total).idSucursal(v.getSucursal().getId()).detalle(detalle).build();
  }

  // Mapeo de DetalleVenta a DetalleVentaDTO
  private static DetalleVentaDTO toDTO(DetalleVenta d) {
    if (d == null) {
      return null;
    }
    return DetalleVentaDTO.builder().id(d.getId()).nombreProducto(d.getProducto().getNombre())
        .cantidadProducto(d.getCantidad()).precio(d.getPrecio())
        .subtotal(d.getPrecio() * d.getCantidad()).build();
  }

}
