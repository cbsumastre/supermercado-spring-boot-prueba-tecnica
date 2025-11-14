package com.firstflip.supermarket.service;

import java.util.List;
import org.springframework.lang.NonNull;
import com.firstflip.supermarket.dto.ProductoDTO;

public interface IProductoService {

  List<ProductoDTO> findAll();

  ProductoDTO findById(@NonNull Long id);

  ProductoDTO save(@NonNull ProductoDTO sucursalDto);

  ProductoDTO update(@NonNull Long id, @NonNull ProductoDTO sucursalDto);

  void delete(@NonNull Long id);


}
