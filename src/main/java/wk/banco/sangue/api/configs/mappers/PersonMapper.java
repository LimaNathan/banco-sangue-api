package wk.banco.sangue.api.configs.mappers;

import wk.banco.sangue.api.domain.dtos.PersonDTO;
import wk.banco.sangue.api.domain.entities.PersonEntity;

public class PersonMapper {

    private PersonMapper() {

    }

    public static PersonEntity toEntity(PersonDTO dto) {
        return PersonEntity.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .rg(dto.getRg())
                .dataNasc(dto.getData_nasc())
                .sexo(dto.getSexo())
                .mae(dto.getMae())
                .pai(dto.getPai())
                .estado(dto.getEstado())
                .build();
    }

    public static PersonDTO toDTO(PersonEntity entity) {
        return PersonDTO.builder()
                .nome(entity.getNome())
                .cpf(entity.getCpf())
                .rg(entity.getRg())
                .data_nasc(entity.getDataNasc())
                .sexo(entity.getSexo())
                .mae(entity.getMae())
                .pai(entity.getPai())
                .estado(entity.getEstado())
                .altura(entity.getPhysicalAttributes().getAltura())
                .peso(entity.getPhysicalAttributes().getPeso())
                .tipo_sanguineo(entity.getPhysicalAttributes().getTipoSanguineo())
                .email(entity.getContactInfo().getEmail())
                .cep(entity.getAddress().getCep())
                .endereco(entity.getAddress().getEndereco())
                .numero(entity.getAddress().getNumero())
                .bairro(entity.getAddress().getBairro())
                .cidade(entity.getAddress().getCidade())
                .telefone_fixo(entity.getContactInfo().getTelefoneFixo())
                .celular(entity.getContactInfo().getCelular())
                .build();
    }
}
