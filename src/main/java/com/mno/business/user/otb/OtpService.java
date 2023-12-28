package com.mno.business.user.otb;

import com.mno.business.user.entity.User;
import com.mno.business.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepo otpRepo;
    private final GmailSender gmailSender;
    private final UserService userService;


    private Otp findByOtp(int otp) {
        return otpRepo.findByOtp(otp).orElse(null);
    }

    public void sendOtp(String email, String token) {
        Random random = new Random();
        int otpCode = random.nextInt(100000, 999999);
        String mail_body = "your authentication code is :"+ otpCode ;
        gmailSender.sendmail(email,"Login Authentication otp",mail_body);
        Otp createOtp = Otp.builder()
                .gmail(email)
                .token(token)
                .otp(otpCode)
                .build();
        otpRepo.save(createOtp);

    }

    public OtpDtoResponse authenticateByotp(OtpDtoRequest otpDtoRequest) {
        Otp otp = findByOtp(otpDtoRequest.getOtp());
        otpRepo.delete(otp);
        otpRepo.deleteAllByGmail(otp.getGmail());
        if (otpDtoRequest.getGmail().equals(otp.getGmail())) {
            String gmail = otp.getGmail();
            User user = userService.userfindByGmail(gmail);

            OtpDtoResponse otpDtoResponse;
            if (user == null) {
                otpDtoResponse = OtpDtoResponse.builder()
                        .gmail(gmail)
                        .checkotp(true)
                        .logined(false)
                        .massage("User is not register")
                        .build();
            } else {
                otpDtoResponse = OtpDtoResponse.builder()
                        .gmail(gmail)
                        .user(user)
                        .token(otp.getToken())
                        .checkotp(true)
                        .logined(true)
                        .massage("Successful Login")
                        .build();

            }
            return otpDtoResponse;
        } else {
            return OtpDtoResponse.builder()
                    .gmail(otpDtoRequest.getGmail())
                    .checkotp(false)
                    .logined(false)
                    .massage("Not same Otp Try again")
                    .build();
        }
    }

    public OtpDtoResponse registerByotp(OtpDtoRequest otpDtoRequest) {
        Otp otp = findByOtp(otpDtoRequest.getOtp());
        otpRepo.delete(otp);
        otpRepo.deleteAllByGmail(otp.getGmail());

        OtpDtoResponse otpDtoResponse;
        if (otpDtoRequest.getGmail().equals(otp.getGmail())) {
            String gmail = otp.getGmail();
            User user = userService.userfindByGmail(gmail);


            otpDtoResponse = OtpDtoResponse.builder()
                    .gmail(gmail)
                    .user(user)
                    .token(otp.getToken())
                    .checkotp(true)
                    .logined(true)
                    .massage("Successful Login")
                    .build();
            return otpDtoResponse;
        } else {


            return OtpDtoResponse.builder()
                    .gmail(otpDtoRequest.getGmail())
                    .checkotp(false)
                    .logined(false)
                    .massage("email or otp is fail")
                    .build();
        }

    }
}
