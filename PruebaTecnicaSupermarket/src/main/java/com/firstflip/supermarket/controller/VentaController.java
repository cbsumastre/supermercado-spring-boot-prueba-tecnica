package com.firstflip.supermarket.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.firstflip.supermarket.dto.VentaDTO;
import com.firstflip.supermarket.service.SucursalService;
import com.firstflip.supermarket.service.VentaService;



@RestController
@RequestMapping("/api/ventas")
public class VentaController {

  @Autowired
  private VentaService ventaService;

  @Autowired
  private SucursalService sucursalService;

  @GetMapping
  public ResponseEntity<List<VentaDTO>> obtenerVentasConFiltros(
      @RequestParam(name = "sucursalId", required = false) Long sucursalId,
      @RequestParam(name = "fecha", required = false) LocalDate fecha) {
    List<VentaDTO> ventas = ventaService.getAll();
    if (sucursalId != null) {
      sucursalService.getById(sucursalId);
      ventas = ventas.stream().filter(v -> v.getIdSucursal().equals(sucursalId)).toList();
    }
    if (fecha != null) {
      ventas = ventas.stream().filter(v -> v.getFecha().toLocalDate().equals(fecha)).toList();
    }
    return ResponseEntity.ok().body(ventas);
  }

  @GetMapping("/{id}")
  public ResponseEntity<VentaDTO> getById(@NonNull @PathVariable Long id) {
    return ResponseEntity.ok().body(ventaService.getById(id));
  }

  @PostMapping
  public ResponseEntity<VentaDTO> registrarNuevaVenta(@NonNull @RequestBody VentaDTO ventaDTO) {
    var venta = ventaService.create(ventaDTO);
    return ResponseEntity
        .created(Objects.requireNonNull(URI.create("/api/ventas/" + venta.getId()))).body(venta);
  }

  @PutMapping("/{id}")
  public ResponseEntity<VentaDTO> actualizar(@NonNull @PathVariable Long id,
      @NonNull @RequestBody VentaDTO ventaDTO) {
    return ResponseEntity.ok().body(ventaService.update(id, ventaDTO));
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> borrarVenta(@NonNull @PathVariable Long id) {
    ventaService.delete(id);
    return ResponseEntity.noContent().build();
  }


}
