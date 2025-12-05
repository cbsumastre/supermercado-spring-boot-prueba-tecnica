package com.firstflip.supermarket.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.firstflip.supermarket.dto.ProductoDTO;
import com.firstflip.supermarket.exception.NotFoundException;
import com.firstflip.supermarket.mapper.Mapper;
import com.firstflip.supermarket.model.CategoriaProducto;
import com.firstflip.supermarket.model.Producto;
import com.firstflip.supermarket.repository.ProductoRepository;

@Service
public class ProductoService implements IProductoService {

  @Autowired
  private ProductoRepository productoRepository;

  @Override
  public List<ProductoDTO> getAll() {
    return productoRepository.findAll().stream().map(Mapper::toDTO).toList();
  }

  @Override
  public ProductoDTO getById(@NonNull Long id) {
    Optional<Producto> optionalProducto = productoRepository.findById(id);
    if (optionalProducto.isPresent()) {
      return Mapper.toDTO(optionalProducto.get());
    }
    throw new NotFoundException("Producto no encontrado para id " + id);
  }

  @Override
  public ProductoDTO create(@NonNull ProductoDTO productoDto) {
    var producto = Producto.builder().nombre(productoDto.getNombre())
        .categoriaProducto(CategoriaProducto.valueOf(productoDto.getCategoria()))
        .stock(productoDto.getStock()).precio(productoDto.getPrecio()).build();
    producto = productoRepository.save(Objects.requireNonNull(producto));
    return Mapper.toDTO(producto);

  }

  @Override
  public ProductoDTO update(@NonNull Long id, @NonNull ProductoDTO productoDto) {
    Producto producto = productoRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Producto no encontrado para " + id));


    producto.setNombre(productoDto.getNombre());
    producto.setCategoriaProducto(CategoriaProducto.valueOf(productoDto.getCategoria()));
    producto.setStock(productoDto.getStock());
    producto.setPrecio(productoDto.getPrecio());

    producto = productoRepository.save(producto);

    return Mapper.toDTO(producto);
  }

  @Override
  public void delete(@NonNull Long id) {
    boolean existsById = productoRepository.existsById(id);
    if (!existsById) {
      throw new NotFoundException("Producto no encontrado para id " + id);
    }
    productoRepository.deleteById(id);
  }

  public ProductoDTO findByNombre(String nombre) {
    Producto producto = productoRepository.findByNombre(nombre)
        .orElseThrow(() -> new NotFoundException("Producto no encontrado para nombre " + nombre));
    return Mapper.toDTO(producto);
  }

}
