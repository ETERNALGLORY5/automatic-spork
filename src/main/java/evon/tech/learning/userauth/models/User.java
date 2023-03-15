package evon.api.userauth.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "users", schema = "sc_auth")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    @NotEmpty
    public String username;

    @NotEmpty
    public String email;

    @NotEmpty
    public String password;

    public String firstName;

    public String lastName;

//    private Integer otp;
//
//    private LocalDateTime otpExpires;

    private UserStatus status = UserStatus.INACTIVE;

    @ManyToMany
    public List<Role> roles;
}
