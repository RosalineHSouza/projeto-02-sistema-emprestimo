
package com.projeto.emprestimo.service;

import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.model.TipoEquipamento;
import com.projeto.emprestimo.repository.EquipamentoRepository;
import com.projeto.emprestimo.repository.EmprestimoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final EmprestimoRepository emprestimoRepository;

    public EquipamentoService(
            EquipamentoRepository equipamentoRepository,
            EmprestimoRepository emprestimoRepository) {

        this.equipamentoRepository = equipamentoRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    // Lista equipamentos com filtros opcionais
    public List<Equipamento> listar(
            TipoEquipamento tipo, Boolean disponivel) {

        if (tipo != null && disponivel != null) {
            return equipamentoRepository
                    .findByTipoAndDisponivel(tipo, disponivel);
        }

        if (tipo != null) {
            return equipamentoRepository.findByTipo(tipo);
        }

        if (disponivel != null) {
            return equipamentoRepository
                    .findByDisponivel(disponivel);
        }

        return equipamentoRepository.findAll();
    }

    // Busca equipamento pelo ID
    public Equipamento buscar(Long id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Equipamento não encontrado"));
    }

    // R1 e R2: patrimônio único e disponibilidade inicial
    public Equipamento cadastrar(Equipamento equipamento) {

        if (equipamentoRepository.existsByPatrimonio(
                equipamento.getPatrimonio())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Patrimônio já cadastrado");
        }

        equipamento.setId(null);
        equipamento.setDisponivel(true);

        return equipamentoRepository.save(equipamento);
    }

    // Atualiza somente nome e tipo
    public Equipamento atualizar(Long id, Equipamento dados) {

        Equipamento equipamento = buscar(id);

        equipamento.setNome(dados.getNome());
        equipamento.setTipo(dados.getTipo());

        return equipamentoRepository.save(equipamento);
    }

    // R9: impede exclusão se houver histórico
    public void remover(Long id) {

        Equipamento equipamento = buscar(id);

        if (emprestimoRepository.existsByEquipamentoId(id)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Equipamento possui histórico de empréstimos");
        }

        equipamentoRepository.delete(equipamento);
    }
}
