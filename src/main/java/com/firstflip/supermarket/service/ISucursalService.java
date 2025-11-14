package com.firstflip.supermarket.service;

import java.util.List;
import com.firstflip.supermarket.dto.SucursalDTO;

public interface ISucursalService {

  List<SucursalDTO> findAll();

  SucursalDTO findById(Long id);

  // SucursalDTO save(Sucursal sucursal);
  // void save(SucursalDTO sucursalDTO);

  SucursalDTO save(SucursalDTO sucursalDto);

  SucursalDTO update(Long id, SucursalDTO sucursalDto);

  void delete(Long id);

}
