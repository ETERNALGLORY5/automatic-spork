package evon.microservices.userauth.services;

import evon.api.userauth.dto.*;
import evon.microservices.userauth.mapper.UserMapper;
import evon.api.core.exceptions.AlreadyExistsException;
import evon.api.userauth.interfaces.UserService;
import evon.api.userauth.models.Role;
import evon.api.userauth.models.User;
import evon.microservices.userauth.repository.RoleRepository;
import evon.microservices.userauth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;

    @Override
    public SignUpResponseDto signup(SignUpRequestDto signUpRequestDto) {
        User user = userMapper.signUpRequestToUser(signUpRequestDto);
        logger.info("User Data : {}", user);
        Optional<User> userData = userRepository.findUserByEmail(user.getEmail());
        if(userData.isPresent()) {
            throw new AlreadyExistsException("User already exists");
        }
        Optional<User> userData1 = userRepository.findUserByUsername(user.getUsername());
        if(userData1.isPresent()) {
            throw new AlreadyExistsException("User already exists");
        }

        if(!signUpRequestDto.getPassword().equals(signUpRequestDto.getConfirmPassword())) {
            throw new RuntimeException("Password and Confirm password not matched");
        }
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        List<Role> roles = new ArrayList<>();
        Optional<Role> role = roleRepository.findRoleByName("ROLE_USER");
        Role roleObj = role.isPresent() ? role.get() : null;
        roles.add(roleObj);
        user.setRoles(roles);
        user = userRepository.save(user);
        SignUpResponseDto response = userMapper.userToSignupResponse(user);
        return response;
    }

    @Override
    public SignInResponseDto signin(SignInRequestDto signInRequestDto) {
        Authentication authObject = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDto.getUsername(), signInRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authObject);

        final Optional<User> userDetailsObj = userRepository.findUserByUsername(signInRequestDto.getUsername());
        User user = userDetailsObj.get();
        final SignInResponseDto authenticationResponse = new SignInResponseDto();
        authenticationResponse.setToken(jwtTokenService.generateToken(user));
        UserDetailsDto userDetails = userMapper.userToUserDetailsResponse(user);
        authenticationResponse.setUserDetails(userDetails);
        return authenticationResponse;

    }
}
