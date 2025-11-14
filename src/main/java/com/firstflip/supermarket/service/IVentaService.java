package com.firstflip.supermarket.service;

import java.util.List;
import org.springframework.lang.NonNull;
import com.firstflip.supermarket.dto.VentaDTO;

public interface IVentaService {

  List<VentaDTO> getAll();

  VentaDTO getById(@NonNull Long id);

  VentaDTO create(@NonNull VentaDTO ventoDto);

  VentaDTO update(@NonNull Long id, @NonNull VentaDTO ventoDto);

  void delete(@NonNull Long id);


}
