package com.firstflip.supermarket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaDTO {

  private Long id;
  private String nombreProducto;
  private Integer cantidadProducto;
  private Double precio;
  private Double subtotal;

}
