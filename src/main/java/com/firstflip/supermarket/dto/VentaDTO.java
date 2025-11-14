package com.firstflip.supermarket.dto;

import java.time.LocalDateTime;
import java.util.List;
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
public class VentaDTO {

  // datos de la venta
  private Long id;
  private LocalDateTime fecha;
  private String estado;
  private Double total;

  // datos de la sucursal
  private Long idSucursal;

  // detalles de la venta
  private List<DetalleVentaDTO> detalle;

}
