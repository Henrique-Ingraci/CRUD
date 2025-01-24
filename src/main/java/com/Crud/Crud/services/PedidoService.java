package com.Crud.Crud.services;

import com.Crud.Crud.entities.Cliente;
import com.Crud.Crud.entities.Pedido;
import com.Crud.Crud.entities.Produto;
import com.Crud.Crud.repositories.ClienteRepository;
import com.Crud.Crud.repositories.PedidoRepository;
import com.Crud.Crud.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

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

    // Registrar uma nova venda
    public Pedido registrarVenda(Long clienteId, List<Long> produtoIds) {
        // Buscar cliente pelo ID
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clienteId));

        // Buscar produtos pelos IDs
        List<Produto> produtos = produtoRepository.findAllById(produtoIds);
        if (produtos.isEmpty()) {
            throw new RuntimeException("Nenhum produto encontrado para os IDs fornecidos");
        }

        // Calcular o valor total do pedido
        double valorTotal = produtos.stream()
                                    .mapToDouble(Produto::getPreco)
                                    .sum();

        // Criar e salvar o pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProdutos(produtos);
        pedido.setValorTotal(valorTotal);
        pedido.setDataPedido(LocalDate.now());

        return pedidoRepository.save(pedido);
    }
    public List<Pedido> obterHistoricoPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
    public List<Pedido> consultarPedidos(String dataInicial, String dataFinal) {
        if (dataInicial == null && dataFinal == null) {
            return pedidoRepository.findAll();
        }

        LocalDate inicio = dataInicial != null ? LocalDate.parse(dataInicial) : LocalDate.MIN;
        LocalDate fim = dataFinal != null ? LocalDate.parse(dataFinal) : LocalDate.MAX;

        return pedidoRepository.findByDataPedidoBetween(inicio, fim);
    }

}
