package com.firstflip.supermarket.service;

import java.util.List;
import org.springframework.lang.NonNull;
import com.firstflip.supermarket.dto.SucursalDTO;

public interface ISucursalService {

  List<SucursalDTO> getAll();

  SucursalDTO getById(@NonNull Long id);

  // SucursalDTO create(Sucursal sucursal);
  // void create(SucursalDTO sucursalDTO);

  SucursalDTO create(@NonNull SucursalDTO sucursalDto);

  SucursalDTO update(@NonNull Long id, @NonNull SucursalDTO sucursalDto);

  void delete(@NonNull Long id);

}
