package com.firstflip.supermarket.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {

  private Long id;

  private String nombre;

  private String categoriaProducto;

  private Integer stock;

  private BigDecimal precio;

}
