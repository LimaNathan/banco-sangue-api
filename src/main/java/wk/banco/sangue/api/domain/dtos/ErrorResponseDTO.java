package wk.banco.sangue.api.domain.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Getter;



@Getter
@Builder
public class ErrorResponseDTO {

    private int code;
    private String status;
    private String message;
    private String key;
    private String objectName;
    private List<String> errors;




}
