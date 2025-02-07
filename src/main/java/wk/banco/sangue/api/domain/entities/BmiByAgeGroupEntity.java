package wk.banco.sangue.api.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "imc_por_faixa_etaria")
@Getter
@NoArgsConstructor // Adiciona o construtor padrão necessário para o Hibernate
@AllArgsConstructor
@Builder
public class BmiByAgeGroupEntity {

    @Id
    private String faixaEtaria;

    private int totalPessoas;

    private double somaImc;

    private Double imcMedio;

}
