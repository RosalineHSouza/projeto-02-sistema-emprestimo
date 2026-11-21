package com.projeto.emprestimo.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EmprestimoTest {

    @Test
    void deveCalcularPrazoDeDevolucaoDe14Dias() {
        // Arrange (Preparação)
        Long equipamentoId = 1L;
        Long colaboradorId = 100L;
        LocalDate dataEmprestimo = LocalDate.of(2026, 11, 21);
        LocalDate dataEsperada = LocalDate.of(2026, 12, 5); // 14 dias depois

        // Act (Ação)
        Emprestimo emprestimo = new Emprestimo(equipamentoId, colaboradorId, dataEmprestimo);

        // Assert (Verificação)
        assertEquals(dataEsperada, emprestimo.getDataPrevistaDevolucao(),
                "A data prevista de devolução deve ser exatamente 14 dias após a data de empréstimo.");
    }

    @Test
    void deveIniciarEmprestimoSemDataDeDevolucao() {
        // Arrange
        Long equipamentoId = 1L;
        Long colaboradorId = 100L;
        LocalDate dataEmprestimo = LocalDate.of(2026, 11, 21);

        // Act
        Emprestimo emprestimo = new Emprestimo(equipamentoId, colaboradorId, dataEmprestimo);

        // Assert
        assertNull(emprestimo.getDataDevolucao(),
                "Um empréstimo recém-criado deve iniciar com a data de devolução nula.");
    }

    @Test
    void deveRegistrarDataDeDevolucao() {
        // Arrange
        Long equipamentoId = 1L;
        Long colaboradorId = 100L;
        LocalDate dataEmprestimo = LocalDate.of(2026, 11, 21);
        LocalDate dataDevolucao = LocalDate.of(2026, 11, 28);
        Emprestimo emprestimo = new Emprestimo(equipamentoId, colaboradorId, dataEmprestimo);

        // Act
        emprestimo.devolver(dataDevolucao);

        // Assert
        assertEquals(dataDevolucao, emprestimo.getDataDevolucao(),
                "A data de devolução registrada deve ser igual à data informada no método devolver.");
    }

    @Test
    void naoDevePermitirDevolucaoDuplicada() {
        // Arrange
        Long equipamentoId = 1L;
        Long colaboradorId = 100L;
        LocalDate dataEmprestimo = LocalDate.of(2026, 11, 21);
        LocalDate primeiraDevolucao = LocalDate.of(2026, 11, 25);
        LocalDate segundaDevolucao = LocalDate.of(2026, 11, 26);

        Emprestimo emprestimo = new Emprestimo(equipamentoId, colaboradorId, dataEmprestimo);
        emprestimo.devolver(primeiraDevolucao);

        // Act & Assert
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> emprestimo.devolver(segundaDevolucao),
                "Deveria lançar IllegalStateException ao tentar devolver um empréstimo já devolvido."
        );

        assertEquals("Empréstimo já devolvido", exception.getMessage());
    }

    @Test
    void deveIdentificarEmprestimoAtrasado() {
        // Arrange
        Long equipamentoId = 1L;
        Long colaboradorId = 100L;
        LocalDate dataEmprestimo = LocalDate.of(2026, 11, 21); // Previsto para 05/12/2026
        LocalDate dataVerificacao = LocalDate.of(2026, 12, 6); // 1 dia após o prazo
        Emprestimo emprestimo = new Emprestimo(equipamentoId, colaboradorId, dataEmprestimo);

        // Act
        boolean atrasado = emprestimo.estaAtrasadoEm(dataVerificacao);

        // Assert
        assertTrue(atrasado,
                "O empréstimo deve ser considerado atrasado quando não foi devolvido e a data atual é posterior à data prevista.");
    }

    @Test
    void naoDeveConsiderarEmprestimoAtrasadoDentroDoPrazo() {
        // Arrange
        Long equipamentoId = 1L;
        Long colaboradorId = 100L;
        LocalDate dataEmprestimo = LocalDate.of(2026, 11, 21); // Previsto para 05/12/2026
        LocalDate dataNoLimiteDoPrazo = LocalDate.of(2026, 12, 5); // Exatamente no dia previsto
        Emprestimo emprestimo = new Emprestimo(equipamentoId, colaboradorId, dataEmprestimo);

        // Act
        boolean atrasado = emprestimo.estaAtrasadoEm(dataNoLimiteDoPrazo);

        // Assert
        assertFalse(atrasado,
                "O empréstimo não deve ser considerado atrasado no próprio dia da data prevista de devolução.");
    }

    @Test
    void naoDeveConsiderarEmprestimoDevolvidoComoAtrasado() {
        // Arrange
        Long equipamentoId = 1L;
        Long colaboradorId = 100L;
        LocalDate dataEmprestimo = LocalDate.of(2026, 11, 21); // Previsto para 05/12/2026
        LocalDate dataDevolucao = LocalDate.of(2026, 11, 30);
        LocalDate dataVerificacaoFutura = LocalDate.of(2026, 12, 20); // Bem após o prazo

        Emprestimo emprestimo = new Emprestimo(equipamentoId, colaboradorId, dataEmprestimo);
        emprestimo.devolver(dataDevolucao);

        // Act
        boolean atrasado = emprestimo.estaAtrasadoEm(dataVerificacaoFutura);

        // Assert
        assertFalse(atrasado,
                "Um empréstimo que já foi devolvido não deve ser considerado atrasado, mesmo após a data prevista.");
    }






}
