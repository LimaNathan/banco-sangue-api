package wk.banco.sangue.api.domain.dtos;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {

    private String name;
    private String tipoSanguineo;
    private String username;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
        
    }

}
