package wk.banco.sangue.api.domain.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class PersonDTO {

    @NonNull
    private final String nome;

    private final String cpf;

    private final String rg;

    @NonNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private final LocalDate data_nasc;

    @NonNull
    private final String sexo;

    private final String mae;
    private final String pai;
    private final String email;
    private final String cep;
    private final String endereco;
    private final Integer numero;
    private final String bairro;
    private final String cidade;
    @NonNull
    private final String estado;
    private final String telefone_fixo;
    private final String celular;

    @NonNull
    private final Double altura;

    @NonNull
    private final Double peso;

    @NonNull
    private final String tipo_sanguineo;
}
