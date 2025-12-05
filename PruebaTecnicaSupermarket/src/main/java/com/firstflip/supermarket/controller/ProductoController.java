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
import com.firstflip.supermarket.dto.ProductoDTO;
import com.firstflip.supermarket.service.ProductoService;



@RestController
@RequestMapping("/api/productos")
public class ProductoController {

  @Autowired
  private ProductoService productoService;

  @GetMapping
  public ResponseEntity<List<ProductoDTO>> obtenerListadoProductos() {
    return ResponseEntity.ok().body(productoService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable Long id) {
    return ResponseEntity.ok().body(productoService.getById(Objects.requireNonNull(id)));
  }

  @PostMapping
  public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
    ProductoDTO creado = productoService.create(Objects.requireNonNull(productoDTO));

    return ResponseEntity
        .created(Objects.requireNonNull(URI.create(("/api/productos/" + creado.getId()))))
        .body(creado);
    // return ResponseEntity.status(HttpStatus.CREATED)
    // .body(productoService.create(Objects.requireNonNull(productoDTO)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id,
      @RequestBody ProductoDTO productoDTO) {
    return ResponseEntity.ok().body(
        productoService.update(Objects.requireNonNull(id), Objects.requireNonNull(productoDTO)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
    productoService.delete(Objects.requireNonNull(id));
    return ResponseEntity.noContent().build();
  }



}
