package wk.banco.sangue.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import wk.banco.sangue.api.domain.dtos.BmiByAgeGroup;
import wk.banco.sangue.api.services.BmiByAgeGroupService;

@RestController
@RequestMapping("/api/bmi-by-age-group")
@RequiredArgsConstructor
@Tag(name = "BMI By Age Group Controller", description = "Consulta dados sobre IMC médio por faixa etária")
public class BmiByAgeGroupController {

    private final BmiByAgeGroupService bmiByAgeGroupService;

    @Operation(summary = "Obter IMC médio por faixa etária", description = "Retorna uma lista com o IMC médio por grupos de idade")
    @ApiResponse(responseCode = "200", description = "Lista de IMC médio por faixa etária")
    @GetMapping
    public ResponseEntity<List<BmiByAgeGroup>> getBmiByAgeGroup() {
        return ResponseEntity.ok(bmiByAgeGroupService.getBmiByGroupAge());
    }
}
