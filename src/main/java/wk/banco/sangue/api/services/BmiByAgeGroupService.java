package wk.banco.sangue.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wk.banco.sangue.api.configs.mappers.BmiByAgeGroupMapper;
import wk.banco.sangue.api.domain.dtos.BmiByAgeGroup;
import wk.banco.sangue.api.domain.entities.BmiByAgeGroupEntity;
import wk.banco.sangue.api.repositories.BmiByAgeGroupRepository;

@Service
@RequiredArgsConstructor
public class BmiByAgeGroupService {

    private final BmiByAgeGroupRepository repository;

    public List<BmiByAgeGroup> getBmiByGroupAge() {
        final List<BmiByAgeGroupEntity> result = repository.findAll();

        return result.stream().map(BmiByAgeGroupMapper::toDTO).toList();

    }

}
