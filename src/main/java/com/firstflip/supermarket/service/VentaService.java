package com.firstflip.supermarket.service;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.firstflip.supermarket.dto.DetalleVentaDTO;
import com.firstflip.supermarket.dto.VentaDTO;
import com.firstflip.supermarket.exception.NotFoundException;
import com.firstflip.supermarket.mapper.Mapper;
import com.firstflip.supermarket.model.DetalleVenta;
import com.firstflip.supermarket.model.EnumEstadoVenta;
import com.firstflip.supermarket.model.Producto;
import com.firstflip.supermarket.model.Sucursal;
import com.firstflip.supermarket.model.Venta;
import com.firstflip.supermarket.repository.ProductoRepository;
import com.firstflip.supermarket.repository.SucursalRepository;
import com.firstflip.supermarket.repository.VentaRepository;

@Service
public class VentaService implements IVentaService {

  @Autowired
  private VentaRepository ventaRepository;

  @Autowired
  private ProductoRepository productoRepository;

  @Autowired
  private SucursalRepository sucursalRepository;

  @Override
  public List<VentaDTO> getAll() {
    return ventaRepository.findAll().stream().map(Mapper::toDTO).toList();
  }

  @Override
  public VentaDTO getById(@NonNull Long id) {
    Venta venta = ventaRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Venta " + id + " no encontrada"));
    return Mapper.toDTO(venta);
  }

  @Override
  public VentaDTO create(@NonNull VentaDTO ventaDto) {
    Long idSucursal = ventaDto.getIdSucursal();
    if (idSucursal == null) {
      throw new RuntimeException("La venta debe estar asignada a una sucursal");
    }
    List<DetalleVentaDTO> detalleVentaDto = ventaDto.getDetalle();
    if (detalleVentaDto == null || detalleVentaDto.isEmpty()) {
      throw new RuntimeException("La venta debe tener al menos un producto");
    }

    // Buscar la sucursal
    Sucursal sucursal = sucursalRepository.findById(Objects.requireNonNull(idSucursal))
        .orElseThrow(() -> new NotFoundException("Sucursal " + idSucursal + " no encontrada"));

    // Creación de la venta
    Venta venta = new Venta();

    venta.setFecha(ventaDto.getFecha());
    venta.setEstado(EnumEstadoVenta.valueOf(ventaDto.getEstado()));
    venta.setTotal(ventaDto.getTotal());
    venta.setSucursal(sucursal);

    // Lista de detalles
    // List<DetalleVenta> detalle =
    // detalleVentaDto.stream().map(d -> createDetalleVenta(venta, d)).toList();
    // venta.setDetalle(detalle);
    detalleVentaDto.forEach(d -> {
      // Buscar producto
      Producto producto = productoRepository.findByNombre(d.getNombreProducto()).orElseThrow(
          () -> new NotFoundException("Producto " + d.getNombreProducto() + " no encontrado"));

      // Crear detalle venta
      DetalleVenta detalleVenta = new DetalleVenta();
      venta.getDetalle().add(detalleVenta);
      detalleVenta.setId(d.getId());
      detalleVenta.setVenta(venta);
      detalleVenta.setProducto(producto);
      detalleVenta.setCantidad(d.getCantidadProducto());
      detalleVenta.setPrecio(d.getPrecio());


    });


    // guardar en la base de datos
    return Mapper.toDTO(ventaRepository.save(venta));

  }

  @Override
  public VentaDTO update(@NonNull Long id, @NonNull VentaDTO ventaDto) {
    Venta venta = ventaRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Venta " + id + " no encontrada"));

    venta.setFecha(ventaDto.getFecha() != null ? ventaDto.getFecha() : venta.getFecha());
    venta.setEstado(ventaDto.getEstado() != null ? EnumEstadoVenta.valueOf(ventaDto.getEstado())
        : venta.getEstado());
    venta.setTotal(ventaDto.getTotal() != null ? ventaDto.getTotal() : venta.getTotal());

    Long idSucursal = ventaDto.getIdSucursal();
    if (idSucursal != null) {
      // Buscar la sucursal
      Sucursal sucursal = sucursalRepository.findById(Objects.requireNonNull(idSucursal))
          .orElseThrow(() -> new NotFoundException("Sucursal " + idSucursal + " no encontrada"));
      venta.setSucursal(sucursal);
    }

    // guardar en la base de datos
    return Mapper.toDTO(ventaRepository.save(venta));

  }

  @Override
  public void delete(@NonNull Long id) {
    boolean existsById = ventaRepository.existsById(id);
    if (!existsById) {
      throw new NotFoundException("Venta " + id + " no encontrada");
    }
    ventaRepository.deleteById(id);
  }

  private DetalleVenta createDetalleVenta(Venta venta, DetalleVentaDTO d) {
    // Buscar producto
    Producto producto = productoRepository.findByNombre(d.getNombreProducto()).orElseThrow(
        () -> new NotFoundException("Producto " + d.getNombreProducto() + " no encontrado"));

    // Crear detalle venta
    DetalleVenta detalleVenta = new DetalleVenta();
    detalleVenta.setId(d.getId());
    detalleVenta.setVenta(venta);
    detalleVenta.setProducto(producto);
    detalleVenta.setCantidad(d.getCantidadProducto());
    detalleVenta.setPrecio(d.getPrecio());
    return detalleVenta;
  }

}
