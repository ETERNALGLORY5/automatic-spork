package evon.api.core.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {
    private final static Integer LENGTH = 6;

    public static Integer generetaOtp() {
        Random random = new Random();
        StringBuilder oneTimePassword = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int randomNumber = random.nextInt(10);
            oneTimePassword.append(randomNumber);
        }
        return Integer.parseInt(oneTimePassword.toString().trim());
    }
}
