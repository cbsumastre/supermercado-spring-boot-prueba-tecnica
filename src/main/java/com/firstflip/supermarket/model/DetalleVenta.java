package com.firstflip.supermarket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVenta {

  private Long id;

  private Venta venta;

  private Producto producto;

  private Integer cantidad;

  private Double precio;

}
