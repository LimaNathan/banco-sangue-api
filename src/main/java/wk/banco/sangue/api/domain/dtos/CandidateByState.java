package wk.banco.sangue.api.domain.dtos;

import lombok.Value;

@Value
public class CandidateByState {

    String estado;
    Long quantidadeCandidatos;
}
