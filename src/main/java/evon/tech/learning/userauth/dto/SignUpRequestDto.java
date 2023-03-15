package evon.api.userauth.dto;

import evon.api.userauth.models.Role;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
@Data
public class SignUpRequestDto {
    @NotBlank(message = "Email must not be empty")
    public String email;

    @NotBlank(message = "Username must not be empty")
    public String username;

    @NotBlank(message = "Password must not be empty")
    public String password;

    @NotBlank(message = "Confirm Password must not be empty")
    public String confirmPassword;

    @NotBlank(message = "Phone Number must not be empty")
    public String phoneNumber;

    @NotBlank(message = "Country must not be empty")
    public String country;

}
