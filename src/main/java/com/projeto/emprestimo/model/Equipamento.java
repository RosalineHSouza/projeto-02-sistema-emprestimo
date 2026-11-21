
package com.projeto.emprestimo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O patrimônio é obrigatório")
    @Column(nullable = false, unique = true)
    private String patrimonio;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEquipamento tipo;

    @Column(nullable = false)
    private Boolean disponivel = true;

    public Equipamento() {
    }

    public Equipamento(String patrimonio, String nome,
                       TipoEquipamento tipo, Boolean disponivel) {
        this.patrimonio = patrimonio;
        this.nome = nome;
        this.tipo = tipo;
        this.disponivel = disponivel;
    }

    // R4: somente equipamentos disponíveis podem ser emprestados
    public void emprestar() {
        if (!disponivel) {
            throw new IllegalStateException("Equipamento indisponível");
        }
        disponivel = false;
    }

    // R7: o equipamento volta a ficar disponível
    public void devolver() {
        disponivel = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoEquipamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEquipamento tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
