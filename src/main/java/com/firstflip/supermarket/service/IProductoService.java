package com.firstflip.supermarket.service;

import java.util.List;
import org.springframework.lang.NonNull;
import com.firstflip.supermarket.dto.ProductoDTO;

public interface IProductoService {

  List<ProductoDTO> getAll();

  ProductoDTO getById(@NonNull Long id);

  ProductoDTO create(@NonNull ProductoDTO productoDto);

  ProductoDTO update(@NonNull Long id, @NonNull ProductoDTO productoDto);

  void delete(@NonNull Long id);


}
