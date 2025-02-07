package wk.banco.sangue.api.services;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wk.banco.sangue.api.domain.dtos.CustomUserDetails;
import wk.banco.sangue.api.domain.entities.User;
import wk.banco.sangue.api.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Tentando buscar o usuário com o username: {}", username);

        User usuario = userRepository.findByUsername(username).orElseThrow(() -> {
            logger.error("Usuário não encontrado com o username: {}", username);
            throw new UsernameNotFoundException("Usuário não encontrado com o username: " + username);
        });

        logger.info("Usuário encontrado: {}", usuario.getUsername());
        return CustomUserDetails.builder()
                .name(usuario.getUsername())
                .username(usuario.getEmail())
                .tipoSanguineo(usuario.getBloodType())
                .password(usuario.getPassword())
                .build();

    }

}
