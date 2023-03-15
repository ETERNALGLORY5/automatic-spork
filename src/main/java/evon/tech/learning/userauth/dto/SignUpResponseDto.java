package evon.api.userauth.dto;

import lombok.Data;

import java.util.List;

@Data
public class SignUpResponseDto {
    public Long id;

    public String email;

    public String username;

}
