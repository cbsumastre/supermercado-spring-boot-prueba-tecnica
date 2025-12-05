package com.firstflip.supermarket.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty(value = "id")
  private Long id;

  @JsonProperty(value = "fecha")
  private LocalDateTime fecha;

  @JsonProperty(value = "estado")
  private String estado;

  @JsonProperty(value = "total")
  private Double total;

  // datos de la sucursal
  @JsonProperty(value = "sucursal_id")
  private Long idSucursal;

  @JsonProperty(value = "detalle")
  // detalles de la venta
  private List<DetalleVentaDTO> detalle;

}
