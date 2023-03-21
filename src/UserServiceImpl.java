package evon.microservices.userauth.services;

import evon.api.core.services.OtpService;
import evon.api.userauth.dto.*;
import evon.microservices.userauth.mapper.UserMapper;
import evon.api.core.exceptions.AlreadyExistsException;
import evon.api.userauth.interfaces.UserService;
import evon.api.userauth.models.Role;
import evon.api.userauth.models.Users;
import evon.microservices.userauth.reactiverepo.ReactiveUserRepository;
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
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
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
    private ReactiveUserRepository reactiveUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;

    private final static Integer OTP_EXPIRES_MINUTES = 30;

    private static final Integer TOKEN_EXPIRES_MINUTES = 5;

    @Override
    public SignUpResponseDto signup(SignUpRequestDto signUpRequestDto) {
        Users user = userMapper.signUpRequestToUser(signUpRequestDto);
        logger.info("User Data : {}", user);
        Optional<Users> userData = userRepository.findUserByEmail(user.getEmail());
        if(userData.isPresent()) {
            throw new AlreadyExistsException("User already exists");
        }
        Optional<Users> userData1 = userRepository.findUserByUsername(user.getUsername());
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

        final Optional<Users> userDetailsObj = userRepository.findUserByUsername(signInRequestDto.getUsername());
        Users user = userDetailsObj.get();
        final SignInResponseDto authenticationResponse = new SignInResponseDto();
        authenticationResponse.setToken(jwtTokenService.generateToken(user));
        UserDetailsDto userDetails = userMapper.userToUserDetailsResponse(user);
        authenticationResponse.setUserDetails(userDetails);
        return authenticationResponse;

    }

    @Override
    public UserDetailsDto forgetPassword(ForgetPasswordRequestDto forgetPasswordRequestDto) {
        Optional<Users> userData;
        if(!forgetPasswordRequestDto.getUsername().isEmpty() ) {
            userData = userRepository.findUserByUsername(forgetPasswordRequestDto.getUsername());
        } else {
            userData = userRepository.findUserByEmail(forgetPasswordRequestDto.getEmail());
        }
        Integer otp = OtpService.generetaOtp();
        if(userData.isEmpty()) {
            throw new RuntimeException("No user find with given details");
        }
        Users user = userData.get();
        user.setOtp(otp);
        user.setOtpExpires(LocalDateTime.now().plusMinutes(OTP_EXPIRES_MINUTES));
        user = userRepository.save(user);
        UserDetailsDto userDetails = userMapper.userToUserDetailsResponse(user);
        return userDetails;
    }

    @Override
    public VerifyOtpResponseDto verifyOtp(VerifyOtpRequestDto verifyOtpRequestDto) {
        Optional<Users> userData;
        String flag = "";
        String tokenSource = "";
        if(!verifyOtpRequestDto.getUsername().isEmpty() ) {
            userData = userRepository.findUserByUsername(verifyOtpRequestDto.getUsername());
            flag = "username";
            tokenSource = flag + ":" + verifyOtpRequestDto.getUsername();
        } else {
            userData = userRepository.findUserByEmail(verifyOtpRequestDto.getEmail());
            flag = "email";
            tokenSource = flag + ":" + verifyOtpRequestDto.getEmail();
        }

        if(userData.isEmpty()) {
            throw new RuntimeException("No user find with given details");
        }

        Users user = userData.get();

        if(user.getOtp().intValue() != verifyOtpRequestDto.getOtp().intValue() ) {
            throw new RuntimeException("Invalid OTP provided");
        }
        String token = jwtTokenService.generateToken(
                tokenSource + ":" + verifyOtpRequestDto.getOtp()
        , TOKEN_EXPIRES_MINUTES);
        VerifyOtpResponseDto verifyOtpResponseDto = new VerifyOtpResponseDto();
        verifyOtpResponseDto.setToken(token);
        return verifyOtpResponseDto;
    }

    @Override
    public UserDetailsDto setPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
        String decryptedSubject = jwtTokenService.validateTokenAndGetUsername(resetPasswordRequestDto.getToken());

        String[] tokenList = decryptedSubject.split(":");

        Optional<Users> userData;

        if(tokenList[0].equals("username") ) {
            userData = userRepository.findUserByUsernameAndOtp(tokenList[1], Integer.parseInt(tokenList[2]));
        } else {
            userData = userRepository.findUserByEmailAndOtp(tokenList[1], Integer.parseInt(tokenList[2]));
        }

        if(userData.isEmpty()) {
            throw new RuntimeException("No user find with given details");
        }

        if(!resetPasswordRequestDto.getPassword().equals(resetPasswordRequestDto.getConfirmPassword())) {
            throw new RuntimeException("Password and Confirm password is not same.");
        }
        Users user = userData.get();
        String encodedPassword = passwordEncoder.encode(resetPasswordRequestDto.getPassword());
        user.setPassword(encodedPassword);
        Users savedUser = userRepository.save(user);
        UserDetailsDto userDetails = userMapper.userToUserDetailsResponse(savedUser);
        return userDetails;
    }

    @Override
    public UserDetailsDto resendOtp(ForgetPasswordRequestDto forgetPasswordRequestDto) {
        return this.forgetPassword(forgetPasswordRequestDto);
    }

    public Flux<Users> getAllUsers() {
        return reactiveUserRepository.findAll();
    }
}
