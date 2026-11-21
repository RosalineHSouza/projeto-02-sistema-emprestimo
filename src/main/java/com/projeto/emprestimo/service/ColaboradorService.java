
package com.projeto.emprestimo.service;

import com.projeto.emprestimo.model.Colaborador;
import com.projeto.emprestimo.repository.ColaboradorRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorService(
            ColaboradorRepository colaboradorRepository) {

        this.colaboradorRepository = colaboradorRepository;
    }

    // Lista todos os colaboradores
    public List<Colaborador> listar() {
        return colaboradorRepository.findAll();
    }

    // Busca colaborador pelo ID
    public Colaborador buscar(Long id) {
        return colaboradorRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Colaborador não encontrado"));
    }

    // R3: impede cadastro com e-mail duplicado
    public Colaborador cadastrar(Colaborador colaborador) {

        if (colaboradorRepository.existsByEmail(
                colaborador.getEmail())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "E-mail já cadastrado");
        }

        colaborador.setId(null);

        return colaboradorRepository.save(colaborador);
    }
}
