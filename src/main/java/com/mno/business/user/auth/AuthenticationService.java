package com.mno.business.user.auth;

import com.mno.business.config.JwtService;
import com.mno.business.user.dto.UserDto;
import com.mno.business.user.entity.Role;
import com.mno.business.user.entity.Token;
import com.mno.business.user.entity.TokenType;
import com.mno.business.user.entity.User;
import com.mno.business.user.otb.OtpService;
import com.mno.business.user.repo.TokenRepo;
import com.mno.business.user.repo.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Data
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final TokenRepo tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;
    private final UserDto userDto;

    @Value("${my-user.admin.gmail}")
    private String adminGmail;


    public UserDto register(RegisterRequest request) {
        User user;

        if (Objects.equals(request.gmail, getAdminGmail())) {

            user = User.builder()
                    .name(request.name)
                    .gmail(request.gmail)
                    .address(request.address)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ADMIN)
                    .build();
            var savedUser = userRepo.save(user);
            var jwtToken = jwtService.generateToken(user);
            saveUserToken(savedUser, jwtToken);
            otpService.sendOtp(request.getGmail(), jwtToken);
            return userDto.mapper(savedUser);

        } else {

            user = User.builder()
                    .name(request.name)
                    .gmail(request.gmail)
                    .address(request.address)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            var savedUser = userRepo.save(user);
            var jwtToken = jwtService.generateToken(user);

            System.out.printf(jwtToken);

            saveUserToken(savedUser, jwtToken);
            otpService.sendOtp(request.getGmail(), jwtToken);
            return userDto.mapper(savedUser);
        }


    }

    public UserDto authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getGmail(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByGmail(request.getGmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        removeAllTokenByUser(user);
        saveUserToken(user, jwtToken);
        otpService.sendOtp(request.getGmail(), jwtToken);
        return userDto.mapper(user);

    }

    public UserDto forGetPass(AuthenticationRequest request) {

        User user = userRepo.findByGmail(request.getGmail()).orElse(null);

        var jwtToken = jwtService.generateToken(user);
        removeAllTokenByUser(user);
        saveUserToken(user, jwtToken);
        otpService.sendOtp(request.getGmail(), jwtToken);

        return userDto.mapper(user);

    }

    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void removeAllTokenByUser(User user) {
        tokenRepository.deleteAllByUser(user.getId());
    }
}

