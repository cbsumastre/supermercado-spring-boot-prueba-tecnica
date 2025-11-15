package com.firstflip.supermarket.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

  private Long id;

  private LocalDateTime fecha;

  private EnumEstadoVenta estado;

  private Double total;

  private Sucursal sucursal;

  private List<DetalleVenta> detalle = new ArrayList<>();

}
