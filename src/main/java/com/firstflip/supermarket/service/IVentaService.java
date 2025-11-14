package com.firstflip.supermarket.service;

import java.util.List;
import com.firstflip.supermarket.dto.VentaDTO;

public interface IVentaService {

  List<VentaDTO> findAll();

  VentaDTO findById(Long id);

  VentaDTO save(VentaDTO sucursalDto);

  VentaDTO update(Long id, VentaDTO sucursalDto);

  void delete(Long id);


}
