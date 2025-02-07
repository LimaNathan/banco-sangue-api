package wk.banco.sangue.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import wk.banco.sangue.api.domain.entities.BmiByAgeGroupEntity;

public interface BmiByAgeGroupRepository extends JpaRepository<BmiByAgeGroupEntity, String> {

}
