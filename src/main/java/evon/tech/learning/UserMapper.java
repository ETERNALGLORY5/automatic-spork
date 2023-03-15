package evon.microservices.userauth.mapper;

import evon.api.userauth.dto.SignUpRequestDto;
import evon.api.userauth.dto.SignUpResponseDto;
import evon.api.userauth.dto.UserDetailsDto;
import evon.api.userauth.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User signUpRequestToUser(SignUpRequestDto signUpRequestDto);
    SignUpResponseDto userToSignupResponse(User user);

    UserDetailsDto userToUserDetailsResponse(User user);
}