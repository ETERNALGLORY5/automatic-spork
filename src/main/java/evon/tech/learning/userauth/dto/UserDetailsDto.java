package evon.api.userauth.dto;

import lombok.Data;

@Data
public class UserDetailsDto {
    public Long id;

    public String email;

    public String username;
}
