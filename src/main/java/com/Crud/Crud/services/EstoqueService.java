package com.Crud.Crud.services;

import com.Crud.Crud.entities.Produto;
import com.Crud.Crud.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    public void verificarEstoque() {
        List<Produto> produtos = produtoRepository.findAll();

        for (Produto produto : produtos) {
            if (produto.getQuantidade() < 10) { // Limite de estoque crítico
                String mensagem = "O produto " + produto.getNome() + 
                                  " está com estoque baixo. Quantidade atual: " + 
                                  produto.getQuantidade() + ".";
                emailNotificationService.enviarNotificacaoReabastecimento("gerente@empresa.com", mensagem);
            }
        }
    }
    @Scheduled(fixedRate = 3600000) // Executa a cada 1 hora
    public void verificarEstoqueAgendado() {
        verificarEstoque();
    }
}
