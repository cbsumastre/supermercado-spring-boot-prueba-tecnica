package com.firstflip.supermarket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty(value = "id")
  private Long id;
  @JsonProperty(value = "nombre_producto")
  private String nombreProducto;
  @JsonProperty(value = "cantidad_producto")
  private Integer cantidadProducto;
  @JsonProperty(value = "precio")
  private Double precio;
  @JsonProperty(value = "subtotal")
  private Double subtotal;

}
