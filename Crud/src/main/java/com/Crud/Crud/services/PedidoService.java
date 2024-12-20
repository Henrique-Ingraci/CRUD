package com.Crud.Crud.services;

import com.Crud.Crud.entities.Pedido;
import com.Crud.Crud.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Criar um novo pedido
    public Pedido criarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Listar todos os pedidos
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    // Buscar pedido por ID
    public Pedido buscarPedido(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElse(null);
    }

    // Atualizar um pedido
    public Pedido atualizarPedido(Long id, Pedido pedido) {
        if (pedidoRepository.existsById(id)) {
            pedido.setId(id);
            return pedidoRepository.save(pedido);
        } else {
            return null;
        }
    }

    // Deletar um pedido
    public void deletarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
