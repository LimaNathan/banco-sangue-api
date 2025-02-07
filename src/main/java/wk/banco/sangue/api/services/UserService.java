package wk.banco.sangue.api.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wk.banco.sangue.api.configs.exception.runtime.WkSangueException;
import wk.banco.sangue.api.domain.dtos.RegisterDTO;
import wk.banco.sangue.api.domain.entities.User;
import wk.banco.sangue.api.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new WkSangueException(HttpStatus.NOT_ACCEPTABLE, "Usuário já existe.");
        }
        User user = User.builder()
                .username(registerDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(registerDTO.getPassword()))
                .bloodType(registerDTO.getBloodType())
                .email(registerDTO.getEmail())
                .build();
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }
}
