package com.firstflip.supermarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.firstflip.supermarket.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {

}
