package com.Crud.Crud.repositories;

import com.Crud.Crud.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByDataPedidoBetween(LocalDate dataInicial, LocalDate dataFinal);
}
