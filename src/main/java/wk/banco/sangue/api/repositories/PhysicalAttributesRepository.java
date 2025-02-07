package wk.banco.sangue.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wk.banco.sangue.api.domain.entities.PhysicalAttributesEntity;

@Repository
public interface PhysicalAttributesRepository extends JpaRepository<PhysicalAttributesEntity, Long> {

}
