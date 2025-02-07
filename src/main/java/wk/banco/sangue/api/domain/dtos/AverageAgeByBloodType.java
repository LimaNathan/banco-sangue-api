package wk.banco.sangue.api.domain.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AverageAgeByBloodType {
    private final String tipoSanguineo;
    private final BigDecimal idadeMedia;
}
