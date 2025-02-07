package wk.banco.sangue.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import wk.banco.sangue.api.domain.dtos.AverageAgeByBloodType;
import wk.banco.sangue.api.domain.dtos.CandidateByState;
import wk.banco.sangue.api.domain.dtos.EligibleDonorCount;
import wk.banco.sangue.api.domain.dtos.ObesityRateByGender;
import wk.banco.sangue.api.domain.dtos.PersonDTO;
import wk.banco.sangue.api.services.PersonService;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
@Tag(name = "Person Controller", description = "Gerencia candidatos a doação de sangue")
public class PersonController {

    private final PersonService personService;

    @Operation(summary = "Salvar uma nova pessoa", description = "Cadastra uma nova pessoa no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pessoa cadastrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PostMapping
    public ResponseEntity<PersonDTO> save(@RequestBody PersonDTO personDTO) {
        return ResponseEntity.status(201).body(personService.save(personDTO));
    }

    @Operation(summary = "Listar candidatos por estado", description = "Retorna a contagem de candidatos agrupados por estado")
    @ApiResponse(responseCode = "200", description = "Lista de candidatos por estado")
    @GetMapping("/candidates-by-state")
    public ResponseEntity<List<CandidateByState>> getCandidatesByState() {
        return ResponseEntity.ok(personService.getCandidateCountByState());
    }

    @Operation(summary = "Percentual de obesidade por gênero", description = "Calcula a porcentagem de obesidade para homens e mulheres")
    @ApiResponse(responseCode = "200", description = "Dados sobre obesidade por gênero")
    @GetMapping("/obesity-rate")
    public ResponseEntity<List<ObesityRateByGender>> getObesityRateByGender() {
        return ResponseEntity.ok(personService.getObesityRateByGender());
    }

    @Operation(summary = "Média de idade por tipo sanguíneo", description = "Retorna a média de idade das pessoas agrupadas pelo tipo sanguíneo")
    @ApiResponse(responseCode = "200", description = "Média de idade por tipo sanguíneo")
    @GetMapping("/average-age-by-blood-type")
    public ResponseEntity<List<AverageAgeByBloodType>> getAverageAgeByBloodType() {
        return ResponseEntity.ok(personService.getAverageAgeByBloodType());
    }

    @Operation(summary = "Contagem de doadores elegíveis por tipo sanguíneo", description = "Retorna a quantidade de possíveis doadores para cada tipo sanguíneo receptor")
    @ApiResponse(responseCode = "200", description = "Lista de doadores elegíveis")
    @GetMapping("/eligible-donors")
    public ResponseEntity<List<EligibleDonorCount>> getEligibleDonors() {
        return ResponseEntity.ok(personService.getEligibleDonorsForEachBloodType());
    }

    @Operation(summary = "Contagem de doadores elegíveis por tipo sanguíneo", description = "Retorna a quantidade de possíveis doadores para cada tipo sanguíneo receptor")
    @ApiResponse(responseCode = "200", description = "Lista de doadores elegíveis")
    @GetMapping("/total")
    public ResponseEntity<Map<String, Integer>> count() {
         final Map<String, Integer> body = new HashMap<>();

                body.put("count", personService.countPersons());
        return ResponseEntity.ok(body);
    }
}
