package com.firstflip.supermarket.dto;

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
public class SucursalDTO {

  @JsonProperty(value = "id")
  private Long id;

  @JsonProperty(value = "nombre")
  private String nombre;

  @JsonProperty(value = "direccion")
  private String direccion;

}
