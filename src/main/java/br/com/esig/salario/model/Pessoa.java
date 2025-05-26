package br.com.esig.salario.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cidade;

    private String email;

    private String cep;

    private String endereco;

    private String pais;

    private String usuario;

    private String telefone;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;


    public String getFormatarTelefone() {
        if (this.telefone == null) return "";
        this.telefone = this.telefone.replaceAll("[^0-9]", "");
        if (this.telefone.length() == 10) {
            return String.format("(%s) %s-%s",
                    this.telefone.substring(0, 2),
                    this.telefone.substring(2, 6),
                    this.telefone.substring(6));
        } else if (telefone.length() == 11) {
            return String.format("(%s) %s-%s",
                    this.telefone.substring(0, 2),
                    this.telefone.substring(2, 7),
                    this.telefone.substring(7));
        }
        return this.telefone;
    }

    public String getFormatarCep() {
        if (this.cep == null) return "";
        this.cep = this.cep.replaceAll("[^0-9]", "");
        if (this.cep.length() == 8) {
            return this.cep.substring(0, 5) + "-" + this.cep.substring(5);
        }
        return this.cep;
    }

    public String getTelefoneSemMascara() {
        if (this.telefone == null) return "";
        return this.telefone.replaceAll("[^0-9]", "");
    }

    public String getCepSemMascara() {
        if (this.cep == null) return "";
        return this.cep.replaceAll("[^0-9]", "");
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Pessoa pessoa = (Pessoa) o;
        return getId() != null && Objects.equals(getId(), pessoa.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
