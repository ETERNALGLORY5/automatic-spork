package evon.api.userauth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignInRequestDto {
    @NotEmpty(message = "Username must not be empty")
    public String username;

    @NotEmpty(message = "Password must not be empty")
    public String password;
}
