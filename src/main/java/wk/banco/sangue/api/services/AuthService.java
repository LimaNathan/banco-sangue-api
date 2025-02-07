package wk.banco.sangue.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wk.banco.sangue.api.configs.exception.runtime.WkSangueException;
import wk.banco.sangue.api.configs.jwt.JwtTokenUtil;
import wk.banco.sangue.api.domain.dtos.AuthenticationResponse;
import wk.banco.sangue.api.domain.dtos.CustomUserDetails;
import wk.banco.sangue.api.domain.dtos.LoginDTO;
import wk.banco.sangue.api.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsServiceImpl userService;

    private final UserRepository userRepository;

    private final JwtTokenUtil tokenUtil;

    public AuthenticationResponse login(LoginDTO loginDTO) {
        log.info("Tentando fazer login para o username: {}", loginDTO.getUsername());
        userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new WkSangueException(HttpStatus.BAD_REQUEST, "Você não tem cadastro."));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        if (authentication.isAuthenticated()) {
            log.info("Usuário autenticado com sucesso: {}", loginDTO.getUsername());

            final UserDetails userDetails = userService.loadUserByUsername(loginDTO.getUsername());

            String token = tokenUtil.generateToken(userDetails);
            String refreshToken = tokenUtil
                    .generateRefreshToken(userService.loadUserByUsername(loginDTO.getUsername()));

            return new AuthenticationResponse(token, refreshToken);
        } else {
            throw new WkSangueException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }
    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        final boolean isTokenExpired = tokenUtil.isTokenExpired(refreshToken);
        if (isTokenExpired) {
            throw new WkSangueException(HttpStatus.UNAUTHORIZED, "Sessão expirada, faça o login novamente.");
        }

        String username = tokenUtil.extractUsername(refreshToken);
        CustomUserDetails userDetails = (CustomUserDetails) userService.loadUserByUsername(username);

        String newAccessToken = tokenUtil.generateToken(userDetails);

        return new AuthenticationResponse(newAccessToken, refreshToken);
    }

    public boolean checkTokenValidity(String token) {
        try {
            String username = tokenUtil.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(username);

            return tokenUtil.validateToken(token, userDetails);
        } catch (Exception e) {
            return false;
        }

    }

}
