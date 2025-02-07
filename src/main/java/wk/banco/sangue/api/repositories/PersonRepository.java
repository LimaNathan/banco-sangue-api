package wk.banco.sangue.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import wk.banco.sangue.api.domain.dtos.AverageAgeByBloodType;
import wk.banco.sangue.api.domain.dtos.CandidateByState;
import wk.banco.sangue.api.domain.dtos.EligibleDonorCount;
import wk.banco.sangue.api.domain.dtos.ObesityRateByGender;
import wk.banco.sangue.api.domain.entities.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query(value = """
            SELECT estado, COUNT(*) AS quantidade_candidatos
            FROM person
            GROUP BY estado
            ORDER BY quantidade_candidatos DESC;
                """, nativeQuery = true)
    List<CandidateByState> countCandidateByStates();

    @Query(value = """
                SELECT p.sexo,
                   COUNT(*) AS totalPessoas,
                   SUM(CASE WHEN pa.bmi > 30 THEN 1 ELSE 0 END) AS totalObesos,
                   (SUM(CASE WHEN pa.bmi > 30 THEN 1 ELSE 0 END) / COUNT(*)) * 100 AS percentualObesos
            FROM person p
            JOIN physical_attributes pa ON p.physical_attributes_id = pa.id
            GROUP BY p.sexo
            """, nativeQuery = true)
    List<ObesityRateByGender> findObesityRateByGender();

    @Query(value = """
                 SELECT pa.tipo_sanguineo AS tipoSanguineo,
                   AVG(YEAR(CURRENT_DATE) - YEAR(p.data_nasc)) AS idadeMedia
            FROM person p
            JOIN physical_attributes pa ON p.physical_attributes_id = pa.id
            GROUP BY pa.tipo_sanguineo
            """, nativeQuery = true)
    List<AverageAgeByBloodType> findAverageAgeByBloodType();

    @Query(value = """
            SELECT receptor_tipo.tipo_sanguineo,
                   SUM(1) AS doadores
            FROM (
                SELECT 'A+' AS tipo_sanguineo UNION ALL
                SELECT 'A-' UNION ALL
                SELECT 'B+' UNION ALL
                SELECT 'B-' UNION ALL
                SELECT 'AB+' UNION ALL
                SELECT 'AB-' UNION ALL
                SELECT 'O+' UNION ALL
                SELECT 'O-'
            ) AS receptor_tipo
            LEFT JOIN person doador
                ON TIMESTAMPDIFF(YEAR, doador.data_nasc, CURRENT_DATE) BETWEEN 16 AND 69
                AND doador.physical_attributes_id IS NOT NULL
            LEFT JOIN physical_attributes doador_attr
                ON doador.physical_attributes_id = doador_attr.id
            WHERE (
                (receptor_tipo.tipo_sanguineo = 'A+' AND doador_attr.tipo_sanguineo IN ('A+', 'O-'))
                OR (receptor_tipo.tipo_sanguineo = 'A-' AND doador_attr.tipo_sanguineo IN ('A-', 'O-'))
                OR (receptor_tipo.tipo_sanguineo = 'B+' AND doador_attr.tipo_sanguineo IN ('B+', 'O-'))
                OR (receptor_tipo.tipo_sanguineo = 'B-' AND doador_attr.tipo_sanguineo IN ('B-', 'O-'))
                OR (receptor_tipo.tipo_sanguineo = 'AB+' AND doador_attr.tipo_sanguineo IN ('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'))
                OR (receptor_tipo.tipo_sanguineo = 'AB-' AND doador_attr.tipo_sanguineo IN ('A-', 'B-', 'AB-', 'O-'))
                OR (receptor_tipo.tipo_sanguineo = 'O+' AND doador_attr.tipo_sanguineo IN ('O+', 'O-'))
                OR (receptor_tipo.tipo_sanguineo = 'O-' AND doador_attr.tipo_sanguineo IN ('O-'))
            )
            GROUP BY receptor_tipo.tipo_sanguineo
            ORDER BY receptor_tipo.tipo_sanguineo;


            """, nativeQuery = true)
    List<EligibleDonorCount> countEligibleDonorsForEachBloodType();
}
