package br.com.esig.salario.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "agendamento_novo_salario_cargo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoNovoSalarioCargo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_atual_id", nullable = false)
    private Cargo cargoAtual;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_troca_id", nullable = false)
    private Cargo cargoTroca;
}
