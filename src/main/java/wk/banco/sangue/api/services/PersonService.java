package wk.banco.sangue.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wk.banco.sangue.api.configs.mappers.PersonMapper;
import wk.banco.sangue.api.domain.dtos.AverageAgeByBloodType;
import wk.banco.sangue.api.domain.dtos.CandidateByState;
import wk.banco.sangue.api.domain.dtos.EligibleDonorCount;
import wk.banco.sangue.api.domain.dtos.ObesityRateByGender;
import wk.banco.sangue.api.domain.dtos.PersonDTO;
import wk.banco.sangue.api.domain.entities.AddressEntity;
import wk.banco.sangue.api.domain.entities.ContactInfoEntity;
import wk.banco.sangue.api.domain.entities.PersonEntity;
import wk.banco.sangue.api.domain.entities.PhysicalAttributesEntity;
import wk.banco.sangue.api.repositories.AddressRepository;
import wk.banco.sangue.api.repositories.ContactInfoRepository;
import wk.banco.sangue.api.repositories.PersonRepository;
import wk.banco.sangue.api.repositories.PhysicalAttributesRepository;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final PhysicalAttributesRepository physicalAttributesRepository;

    public List<CandidateByState> getCandidateCountByState() {
        return personRepository.countCandidateByStates();
    }

    public List<ObesityRateByGender> getObesityRateByGender() {
        return personRepository.findObesityRateByGender();
    }

    public List<AverageAgeByBloodType> getAverageAgeByBloodType() {
        return personRepository.findAverageAgeByBloodType();
    }

    public List<EligibleDonorCount> getEligibleDonorsForEachBloodType() {
        return personRepository.countEligibleDonorsForEachBloodType();
    }

    public Integer countPersons() {
        return personRepository.findAll().size();
    }

    @Transactional
    public PersonDTO save(PersonDTO dto) {

        PhysicalAttributesEntity physicalAttributes = PhysicalAttributesEntity.builder()
                .altura(dto.getAltura())
                .peso(dto.getPeso())
                .tipoSanguineo(dto.getTipo_sanguineo())
                .build();
        physicalAttributes = physicalAttributesRepository.save(physicalAttributes);

        AddressEntity address = AddressEntity.builder()
                .cep(dto.getCep())
                .endereco(dto.getEndereco())
                .numero(dto.getNumero())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .build();
        address = addressRepository.save(address);

        ContactInfoEntity contactInfo = ContactInfoEntity.builder()
                .email(dto.getEmail())
                .telefoneFixo(dto.getTelefone_fixo())
                .celular(dto.getCelular())
                .build();
        contactInfo = contactInfoRepository.save(contactInfo);

        PersonEntity person = PersonEntity.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .rg(dto.getRg())
                .dataNasc(dto.getData_nasc())
                .sexo(dto.getSexo())
                .mae(dto.getMae())
                .pai(dto.getPai())
                .address(address)
                .contactInfo(contactInfo)
                .physicalAttributes(physicalAttributes)
                .estado(dto.getEstado())
                .build();

        final PersonEntity savedPerson = personRepository.save(person);

        return PersonMapper.toDTO(savedPerson);

    }
}
