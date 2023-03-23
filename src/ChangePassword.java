//changePassResponseDto.java
@Data
public class ChangePasswordResponseDto {
    private String newPassword;
    private String confirmNewPassword;
}

//changePassRequestDto.java
@Data
public class ChangePasswordRequestDto {
    private String username;
    private Long id;
    @NotBlank(message = " old password is required !")
    private String password;
    @NotBlank(message = "provide new password !")
    private String newPassword;
    @NotBlank(message = "rewrite new password")
    private String confirmNewPassword;
}


//Mapper.java
  Users ChangePasswordRequestToUser(ChangePasswordRequestDto changePasswordRequestDto);
    ChangePasswordResponseDto userToChangePasswordResponseDto(Users user);



//MapperImpl.java
 @Override
    public Users ChangePasswordRequestToUser(ChangePasswordRequestDto changePasswordRequestDto) {
        if ( changePasswordRequestDto == null ) {  return null; }  
        Users users = new Users();
        users.setId( changePasswordRequestDto.getId() );
        users.setUsername( changePasswordRequestDto.getUsername() );
        users.setPassword( changePasswordRequestDto.getPassword() );
        users.setNewPassword( changePasswordRequestDto.getNewPassword() );
        users.setConfirmNewPassword( changePasswordRequestDto.getConfirmNewPassword() );
        return users;
    }
    @Override
    public ChangePasswordResponseDto userToChangePasswordResponseDto(Users user) {
        if ( user == null ) { return null;    }
        ChangePasswordResponseDto changePasswordResponseDto = new ChangePasswordResponseDto();
        changePasswordResponseDto.setNewPassword( user.getNewPassword() );
        changePasswordResponseDto.setConfirmNewPassword( user.getConfirmNewPassword() );
        return changePasswordResponseDto;
    }


//ServiceInterface.java
  @PutMapping("/changePass")
    @ResponseBody
    public ChangePasswordResponseDto changePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto);


//serviceImpl.java

@Override
    public ChangePasswordResponseDto changePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
       // logger.info("UserEmail is : {}", authentication.getCredentials());
        Users users = userMapper.ChangePasswordRequestToUser(changePasswordRequestDto);
       Users userData = userRepository.findById(changePasswordRequestDto.getId()).orElseThrow(() -> new RuntimeException(" Id not found!"));
        if (!changePasswordRequestDto.getNewPassword().equals(changePasswordRequestDto.getConfirmNewPassword())) {
            throw new RuntimeException("Password and Confirm password not matched");
        }
        String password = users.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        users.setPassword(encodedPassword);
        Users save = userRepository.save(users);
        return userMapper.userToChangePasswordResponseDto(save);

    }



//some other changes 
String newPassword 
String ConfirmNewPasword ;
//both are present in Users.java class. with @Transient annotation
