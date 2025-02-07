package wk.banco.sangue.api.domain.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class BmiByAgeGroup {
    private final String faixaEtaria;
    private final double imcMedio;
    private final int totalPessoas;
}
