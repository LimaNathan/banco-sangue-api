package wk.banco.sangue.api.domain.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EligibleDonorCount {
    private final String tipo_sanguineo;
    private final BigDecimal doadores;
}
