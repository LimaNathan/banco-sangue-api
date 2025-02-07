package wk.banco.sangue.api.domain.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginDTO {
    private String username;
    private String password;
}