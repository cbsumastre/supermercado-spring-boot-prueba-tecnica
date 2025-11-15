package com.firstflip.supermarket.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.firstflip.supermarket.model.Venta;

@Repository
public class VentaRepositoryImpl implements VentaRepository {

  @Override
  public List<Venta> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public Optional<Venta> findById(@NonNull Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public boolean existsById(@NonNull Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'existsById'");
  }

  @Override
  public Venta save(@NonNull Venta venta) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public void deleteById(@NonNull Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
  }

}
