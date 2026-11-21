package com.projeto.emprestimo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EquipamentoTest {

    @Test
    void deveEmprestarEquipamentoDisponivel() {

        // Arrange: equipamento inicialmente disponível
        Equipamento equipamento = new Equipamento();

        equipamento.setDisponivel(true);

        // Act: realiza o empréstimo
        equipamento.emprestar();

        // Assert: verifica a indisponibilidade
        assertFalse(equipamento.isDisponivel());

    }

    @Test
    void naoDeveEmprestarEquipamentoIndisponivel() {

        // Arrange: prepara o equipamento
        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(false);

        // Act + Assert: executa e verifica a exceção
        assertThrows(IllegalStateException.class, () -> {
            equipamento.emprestar();
        });
    }

    @Test
    void deveDisponibilizarEquipamentoAposDevolucao() {

        // Arrange: equipamento inicialmente indisponível
        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(false);

        // Act: realiza a devolução
        equipamento.devolver();

        // Assert: verifica a disponibilidade
        assertTrue(equipamento.isDisponivel());
    }

}
