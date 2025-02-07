package wk.banco.sangue.api.domain.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private String rg;

    private LocalDate dataNasc;

    private String sexo;

    private String mae;

    private String pai;

    @OneToOne
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "FK_address"))
    private AddressEntity address;

    @OneToOne
    @JoinColumn(name = "contact_info_id", foreignKey = @ForeignKey(name = "FK_contact_info"))
    private ContactInfoEntity contactInfo;

    @OneToOne
    @JoinColumn(name = "physical_attributes_id", foreignKey = @ForeignKey(name = "FK_physical_attributes"))
    private PhysicalAttributesEntity physicalAttributes;

    private String estado;
}
