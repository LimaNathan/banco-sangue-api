package wk.banco.sangue.api.domain.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ObesityRateByGender {
    private final String sexo;
    private final Long totalPessoas;
    private final BigDecimal totalObesos;
    private final BigDecimal percentualObesos;
}
