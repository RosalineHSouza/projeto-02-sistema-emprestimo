
package com.projeto.emprestimo.controller;

import com.projeto.emprestimo.model.Colaborador;
import com.projeto.emprestimo.model.Emprestimo;

import com.projeto.emprestimo.service.ColaboradorService;
import com.projeto.emprestimo.service.EmprestimoService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;
    private final EmprestimoService emprestimoService;

    public ColaboradorController(
            ColaboradorService colaboradorService,
            EmprestimoService emprestimoService) {

        this.colaboradorService = colaboradorService;
        this.emprestimoService = emprestimoService;
    }

    // Cadastra um colaborador
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Colaborador cadastrar(
            @RequestBody Colaborador colaborador) {

        return colaboradorService.cadastrar(colaborador);
    }

    // Lista todos os colaboradores
    @GetMapping
    public List<Colaborador> listar() {

        return colaboradorService.listar();
    }

    // Busca colaborador pelo ID
    @GetMapping("/{id}")
    public Colaborador buscar(@PathVariable Long id) {

        return colaboradorService.buscar(id);
    }

    // Lista os empréstimos de um colaborador
    @GetMapping("/{id}/emprestimos")
    public List<Emprestimo> listarEmprestimos(
            @PathVariable Long id) {

        return emprestimoService.listarDoColaborador(id);
    }
}
