package com.firstflip.supermarket.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import com.firstflip.supermarket.model.Venta;

public interface VentaRepository {

  List<Venta> findAll();

  Optional<Venta> findById(@NonNull Long id);

  boolean existsById(@NonNull Long id);

  Venta save(@NonNull Venta venta);

  void deleteById(@NonNull Long id);
}
