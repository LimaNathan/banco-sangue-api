package wk.banco.sangue.api.domain.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterDTO {
    private String username;
    private String password;
    private String bloodType;
    private String email;
}
