package com.firstflip.supermarket.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class DetalleVenta {

  private Long id;
  private Long idVenta;
  private Long idProducto;
  private Integer cantidad;
  private BigDecimal precioUnidad;

}
