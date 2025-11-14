package com.firstflip.supermarket.service;

import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.firstflip.supermarket.dto.ProductoDTO;

@Service
public class ProductoService implements IProductoService {

  @Override
  public List<ProductoDTO> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public ProductoDTO findById(@NonNull Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public ProductoDTO save(@NonNull ProductoDTO sucursalDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public ProductoDTO update(@NonNull Long id, @NonNull ProductoDTO sucursalDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(@NonNull Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }



}
