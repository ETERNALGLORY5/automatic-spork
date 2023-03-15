package evon.api.userauth.dto;

import evon.api.userauth.models.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignInResponseDto {
    public String token;

    public UserDetailsDto userDetails;
}
