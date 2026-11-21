
package com.projeto.emprestimo.service;

import com.projeto.emprestimo.model.Colaborador;
import com.projeto.emprestimo.model.Emprestimo;
import com.projeto.emprestimo.model.Equipamento;

import com.projeto.emprestimo.repository.ColaboradorRepository;
import com.projeto.emprestimo.repository.EmprestimoRepository;
import com.projeto.emprestimo.repository.EquipamentoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final ColaboradorRepository colaboradorRepository;

    public EmprestimoService(
            EmprestimoRepository emprestimoRepository,
            EquipamentoRepository equipamentoRepository,
            ColaboradorRepository colaboradorRepository) {

        this.emprestimoRepository = emprestimoRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    // R8: lista todos ou apenas os atrasados
    public List<Emprestimo> listar(Boolean atrasados) {

        if (Boolean.TRUE.equals(atrasados)) {
            return emprestimoRepository
                    .findByDataDevolucaoIsNullAndDataPrevistaDevolucaoBefore(
                            LocalDate.now());
        }

        return emprestimoRepository.findAll();
    }

    // Busca empréstimo pelo ID
    public Emprestimo buscar(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Empréstimo não encontrado"));
    }

    // Lista o histórico de um colaborador
    public List<Emprestimo> listarDoColaborador(
            Long colaboradorId) {

        return emprestimoRepository
                .findByColaboradorId(colaboradorId);
    }

    // R4, R5 e R10: realiza o empréstimo
    @Transactional
    public Emprestimo emprestar(
            Long equipamentoId, Long colaboradorId) {

        Equipamento equipamento = equipamentoRepository
                .findById(equipamentoId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Equipamento não encontrado"));

        if (!colaboradorRepository.existsById(colaboradorId)) {
            throw new IllegalStateException(
                    "Colaborador não encontrado");
        }

        LocalDate hoje = LocalDate.now();

        // R10: impede empréstimo para quem tem atraso
        if (emprestimoRepository
                .existsByColaboradorIdAndDataDevolucaoIsNullAndDataPrevistaDevolucaoBefore(
                        colaboradorId, hoje)) {

            throw new IllegalStateException(
                    "Colaborador possui empréstimo atrasado");
        }

        // R4: verifica e altera a disponibilidade
        equipamento.emprestar();

        // R5: registra hoje e prevê devolução em 14 dias
        Emprestimo emprestimo = new Emprestimo(
                equipamentoId, colaboradorId, hoje);

        equipamentoRepository.save(equipamento);

        return emprestimoRepository.save(emprestimo);
    }

    // R6 e R7: registra a devolução
    @Transactional
    public Emprestimo devolver(Long id) {

        Emprestimo emprestimo = buscar(id);

        // R6: impede devolução duplicada
        emprestimo.devolver(LocalDate.now());

        Equipamento equipamento = equipamentoRepository
                .findById(emprestimo.getEquipamentoId())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Equipamento não encontrado"));

        // R7: equipamento volta a ficar disponível
        equipamento.devolver();

        equipamentoRepository.save(equipamento);

        return emprestimoRepository.save(emprestimo);
    }
}
