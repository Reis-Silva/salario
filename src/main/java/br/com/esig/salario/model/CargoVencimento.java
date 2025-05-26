package br.com.esig.salario.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "cargo_vencimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargoVencimento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vencimento_id")
    private Vencimento vencimento;
}
