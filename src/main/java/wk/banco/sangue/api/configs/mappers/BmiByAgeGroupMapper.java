package wk.banco.sangue.api.configs.mappers;

import wk.banco.sangue.api.domain.dtos.BmiByAgeGroup;
import wk.banco.sangue.api.domain.entities.BmiByAgeGroupEntity;

public class BmiByAgeGroupMapper {

    private BmiByAgeGroupMapper() {
    }

    public static BmiByAgeGroupEntity toEntity(BmiByAgeGroup dto) {
        return BmiByAgeGroupEntity.builder()
                .faixaEtaria(dto.getFaixaEtaria())
                .imcMedio(dto.getImcMedio())
                .totalPessoas(dto.getTotalPessoas())
                .build();
    }

    public static BmiByAgeGroup toDTO(BmiByAgeGroupEntity entity) {
        return BmiByAgeGroup.builder()
                .faixaEtaria(entity.getFaixaEtaria())
                .imcMedio(entity.getImcMedio() != null ? entity.getImcMedio()
                        : 0.0)
                .totalPessoas(entity.getTotalPessoas())
                .build();
    }
}
