package com.firstflip.supermarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

  private Long id;

  private String nombre;

  private String categoriaProducto;

  private Integer stock;

  private Double precio;

}
