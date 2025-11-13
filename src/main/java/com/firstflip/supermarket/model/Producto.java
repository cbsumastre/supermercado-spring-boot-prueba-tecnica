package com.firstflip.supermarket.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Producto {

  private Long id;
  private String nombre;
  private Integer unidades;
  private BigDecimal precio;


}
