package com.firstflip.supermarket.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import com.firstflip.supermarket.model.Producto;

public interface ProductoRepository {

  List<Producto> findAll();

  Optional<Producto> findById(@NonNull Long id);

  Optional<Producto> findByNombre(@NonNull String name);

  boolean existsById(@NonNull Long id);

  Producto save(@NonNull Producto productoDTO);

  Producto update(@NonNull Long id, @NonNull Producto productoDTO);

  void deleteById(@NonNull Long id);

}
