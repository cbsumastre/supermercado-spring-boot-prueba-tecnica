package com.firstflip.supermarket.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import com.firstflip.supermarket.model.Sucursal;

public interface SucursalRepository {
  List<Sucursal> findAll();

  Optional<Sucursal> findById(@NonNull Long id);

  boolean existsById(@NonNull Long id);

  Sucursal save(@NonNull Sucursal productoDTO);

  void deleteById(@NonNull Long id);
}
