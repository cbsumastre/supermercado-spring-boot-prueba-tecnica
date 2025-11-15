package com.firstflip.supermarket.model;

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
public class Producto {

  private Long id;

  private String nombre;

  private CategoriaProducto categoriaProducto;

  private Integer stock;
  private Double precio;


}
