package com.firstflip.supermarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.firstflip.supermarket.model.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

}
