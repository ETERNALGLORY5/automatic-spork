package evon.api.userauth.dto;

import evon.util.validators.OneNotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@OneNotNull(
        fields = {"username","email"},
        message="Either username or email must be set"
)
@Data
public class ForgetPasswordRequestDto {
    public String email;

    public String username;
}
