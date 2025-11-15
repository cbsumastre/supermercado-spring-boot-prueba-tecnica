package com.firstflip.supermarket.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.firstflip.supermarket.dto.DetalleVentaDTO;
import com.firstflip.supermarket.dto.ProductoDTO;
import com.firstflip.supermarket.service.ProductoService;
import com.firstflip.supermarket.service.VentaService;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

  @Autowired
  private ProductoService productoService;

  @Autowired
  private VentaService ventaService;

  @GetMapping("/producto-mas-vendido")
  public ResponseEntity<ProductoDTO> obtenerProductoMasVendido() {
    List<DetalleVentaDTO> allDetalles = ventaService.getAll().stream()
        .flatMap(venta -> venta.getDetalle().stream()).collect(Collectors.toList());

    Map<String, Integer> productos =
        allDetalles.stream().collect(Collectors.groupingBy(DetalleVentaDTO::getNombreProducto,
            Collectors.summingInt(DetalleVentaDTO::getCantidadProducto)));

    if (!productos.isEmpty()) {
      Optional<String> nombreProductoMasVendido = productos.entrySet().stream()
          .max(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey);
      if (nombreProductoMasVendido.isPresent()) {
        ProductoDTO producto =
            productoService.findByNombre(Objects.requireNonNull(nombreProductoMasVendido.get()));
        return ResponseEntity.ok().body(producto);
      }
    }

    return ResponseEntity.notFound().build();

  }

}
