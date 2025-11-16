package com.firstflip.supermarket.service;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.firstflip.supermarket.dto.SucursalDTO;
import com.firstflip.supermarket.exception.NotFoundException;
import com.firstflip.supermarket.mapper.Mapper;
import com.firstflip.supermarket.model.Sucursal;
import com.firstflip.supermarket.repository.SucursalRepository;

@Service
public class SucursalService implements ISucursalService {

  @Autowired
  private SucursalRepository sucursalRepository;


  @Override
  public List<SucursalDTO> getAll() {
    return sucursalRepository.findAll().stream().map(Mapper::toDTO).toList();
  }

  @Override
  public SucursalDTO getById(@NonNull Long id) {
    var sucursal = sucursalRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Sucursal no encontrada para id " + id));
    return Mapper.toDTO(sucursal);
  }

  @Override
  public SucursalDTO create(@NonNull SucursalDTO sucursalDto) {
    var sucursal = Sucursal.builder().nombre(sucursalDto.getNombre())
        .direccion(sucursalDto.getDireccion()).build();
    sucursal = sucursalRepository.save(Objects.requireNonNull(sucursal));
    return Mapper.toDTO(sucursal);
  }

  @Override
  public SucursalDTO update(@NonNull Long id, @NonNull SucursalDTO sucursalDto) {
    Sucursal sucursal = sucursalRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Sucursal no encontrada para id " + id));

    sucursal.setId(id);
    sucursal.setNombre(sucursalDto.getNombre());
    sucursal.setDireccion(sucursalDto.getDireccion());

    sucursal = sucursalRepository.save(sucursal);

    return Mapper.toDTO(sucursal);
  }

  @Override
  public void delete(@NonNull Long id) {
    if (!sucursalRepository.existsById(id)) {
      throw new NotFoundException("Sucursal no encontrada para id " + id);
    }
    sucursalRepository.deleteById(id);
  }

}
