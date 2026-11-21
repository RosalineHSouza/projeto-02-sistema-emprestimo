
package com.projeto.emprestimo.controller;

import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.model.TipoEquipamento;
import com.projeto.emprestimo.service.EquipamentoService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    public EquipamentoController(
            EquipamentoService equipamentoService) {

        this.equipamentoService = equipamentoService;
    }

    // Lista equipamentos com filtros opcionais
    @GetMapping
    public List<Equipamento> listar(
            @RequestParam(required = false)
            TipoEquipamento tipo,

            @RequestParam(required = false)
            Boolean disponivel) {

        return equipamentoService.listar(tipo, disponivel);
    }

    // Busca equipamento pelo ID
    @GetMapping("/{id}")
    public Equipamento buscar(@PathVariable Long id) {

        return equipamentoService.buscar(id);
    }

    // Cadastra um equipamento
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipamento cadastrar(
            @RequestBody Equipamento equipamento) {

        return equipamentoService.cadastrar(equipamento);
    }

    // Atualiza um equipamento
    @PutMapping("/{id}")
    public Equipamento atualizar(
            @PathVariable Long id,
            @RequestBody Equipamento dados) {

        return equipamentoService.atualizar(id, dados);
    }

    // Remove um equipamento
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {

        equipamentoService.remover(id);
    }
}

