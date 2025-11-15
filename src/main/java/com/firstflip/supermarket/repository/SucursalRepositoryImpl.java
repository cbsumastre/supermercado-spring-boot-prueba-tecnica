package com.firstflip.supermarket.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.firstflip.supermarket.model.Sucursal;

@Repository
public class SucursalRepositoryImpl implements SucursalRepository {

  @Override
  public List<Sucursal> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public Optional<Sucursal> findById(@NonNull Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public boolean existsById(@NonNull Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'existsById'");
  }

  @Override
  public Sucursal save(@NonNull Sucursal productoDTO) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public void deleteById(@NonNull Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
  }

}
