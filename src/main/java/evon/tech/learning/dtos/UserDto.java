package evon.tech.learning.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userId;

   // @Column(name = "user_name")
    private String name;

   // @Column(name="user_email", unique =true)
    private String email;

   // @Column(name= "user_password", length=500)
    private String password;

   // @Column
    private String gender;

   // @Column(length=1000)
    private String about;

   // @Column(name="user_image_name")
    private String imageName;
}
