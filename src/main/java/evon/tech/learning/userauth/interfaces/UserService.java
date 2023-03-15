package evon.api.userauth.interfaces;

import evon.api.userauth.dto.SignInRequestDto;
import evon.api.userauth.dto.SignInResponseDto;
import evon.api.userauth.dto.SignUpResponseDto;
import evon.api.userauth.dto.SignUpRequestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
public interface UserService {

    @PostMapping("/signup")
    @ResponseBody
    public SignUpResponseDto signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto);

    @PostMapping("/signin")
    @ResponseBody
    public SignInResponseDto signin(@Valid @RequestBody SignInRequestDto signInRequestDto);
}
