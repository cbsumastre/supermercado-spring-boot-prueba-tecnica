package com.firstflip.supermarket.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Venta {

  private Long id;
  private Long idSucursal;
  private LocalDateTime fecha;

}
