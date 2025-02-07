package wk.banco.sangue.api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import wk.banco.sangue.api.configs.exception.runtime.WkSangueException;
import wk.banco.sangue.api.domain.dtos.AuthenticationResponse;
import wk.banco.sangue.api.domain.dtos.LoginDTO;
import wk.banco.sangue.api.domain.dtos.RegisterDTO;
import wk.banco.sangue.api.services.AuthService;
import wk.banco.sangue.api.services.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "API para gerenciamento de logins e cadastros")
public class AuthController {

        private final AuthService authService;
        private final UserService service;

        @Operation(summary = "Atualiza o token do usuário com base no refreshToken do mesmo", description = "Autentica o usuário e retorna um token JWT.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso, retorna o novo token JWT.", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(type = "string")) }),
                        @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content)
        })
        @PostMapping("/refresh-token")
        public AuthenticationResponse refreshToken(
                        @Parameter(description = "Objeto contendo email e senha do usuário", required = true) @RequestBody String refreshToken)
                        throws WkSangueException {

                return authService.refreshToken(refreshToken);
        }

        @Operation(summary = "Realizar login", description = "Autentica o usuário e retorna um token JWT.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso, retorna o token JWT.", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(type = "string")) }),
                        @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content)
        })
        @PostMapping("/login")
        public AuthenticationResponse login(
                        @Parameter(description = "Objeto contendo email e senha do usuário", required = true) @RequestBody LoginDTO usuario)
                        throws WkSangueException {

                return authService.login(usuario);
        }

        @Operation(summary = "Registrar um novo usuário", description = "Cadastra um novo usuário no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LoginDTO.class)) }),
                        @ApiResponse(responseCode = "400", description = "Erro ao registrar o usuário", content = @Content)
        })
        @PostMapping("/register")
        public ResponseEntity<Map<String, String>> register(
                        @Parameter(description = "Objeto contendo os detalhes do novo usuário", required = true) @RequestBody @Valid RegisterDTO usuario) {

                service.registerUser(usuario);

                final Map<String, String> body = new HashMap<>();

                body.put("message", "Usuário criado com sucesso. Use seu usuário e senha para ter acesso ao app.");
                return ResponseEntity.ok().body(body);

        }
}
