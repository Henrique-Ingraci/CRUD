package com.Crud.Crud.controllers;

import com.Crud.Crud.entities.Pedido;
import com.Crud.Crud.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Criar um novo pedido
    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.criarPedido(pedido);
        return ResponseEntity.ok(novoPedido);
    }

    // Listar todos os pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    // Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPedido(id);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

    // Atualizar pedido
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedido);
        return pedidoAtualizado != null ? ResponseEntity.ok(pedidoAtualizado) : ResponseEntity.notFound().build();
    }

    // Deletar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

    // Registrar uma nova venda
    @PostMapping("/registrar-venda/{clienteId}")
    public ResponseEntity<Pedido> registrarVenda(@PathVariable Long clienteId, @RequestBody List<Long> produtoIds) {
        try {
            Pedido novoPedido = pedidoService.registrarVenda(clienteId, produtoIds);
            return ResponseEntity.ok(novoPedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Histórico de pedidos de um cliente
    @GetMapping("/cliente/{clienteId}/historico")
    public ResponseEntity<List<Pedido>> historicoPedidosPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.obterHistoricoPorCliente(clienteId);
        return pedidos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(pedidos);
    }

    // Consultar todos os pedidos com filtros opcionais (exemplo: data inicial e final)
    @GetMapping("/consulta")
    public ResponseEntity<List<Pedido>> consultarPedidos(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {
        try {
            List<Pedido> pedidos = pedidoService.consultarPedidos(dataInicial, dataFinal);
            return pedidos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pedidos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
