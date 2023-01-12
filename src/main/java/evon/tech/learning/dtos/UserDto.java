package evon.tech.learning.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;

   // @Column(name ="name")
    private String name;

  //  @Column(name = "user_name")
    private String username;

  //  @Column(name = "user_email",unique=true)
    private String email;

  //  @Column(name = "user_password",length =500)
    private String password;


 //   @Column(name = "user_gender")
    private String gender;

  //  @Column(name = "user_image_name")
    private String imagename;


}
