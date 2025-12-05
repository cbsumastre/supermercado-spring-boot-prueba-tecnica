package com.firstflip.supermarket.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.firstflip.supermarket.dto.SucursalDTO;
import com.firstflip.supermarket.service.SucursalService;



@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

  @Autowired
  private SucursalService sucursalService;

  @GetMapping
  public ResponseEntity<List<SucursalDTO>> obtenerListadoSucursales() {
    return ResponseEntity.ok().body(sucursalService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SucursalDTO> getSucursal(@PathVariable Long id) {
    return ResponseEntity.ok().body(sucursalService.getById(Objects.requireNonNull(id)));
  }

  @PostMapping
  public ResponseEntity<SucursalDTO> registrarNuevaSucursal(@RequestBody SucursalDTO sucursalDTO) {
    SucursalDTO sucursal = sucursalService.create(Objects.requireNonNull(sucursalDTO));
    return ResponseEntity
        .created(Objects.requireNonNull(URI.create("/api/sucursales/" + sucursal.getId())))
        .body(sucursal);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SucursalDTO> actualizarSucursalExistente(@PathVariable Long id,
      @RequestBody SucursalDTO sucursalDTO) {
    return ResponseEntity.ok().body(
        sucursalService.update(Objects.requireNonNull(id), Objects.requireNonNull(sucursalDTO)));
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
    sucursalService.delete(Objects.requireNonNull(id));
    return ResponseEntity.noContent().build();
  }



}
