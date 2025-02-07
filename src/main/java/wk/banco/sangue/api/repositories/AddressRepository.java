package wk.banco.sangue.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wk.banco.sangue.api.domain.entities.AddressEntity;


@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
