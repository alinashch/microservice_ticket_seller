package com.example.user_module.service;


import com.example.user_module.exception.*;
import com.example.user_module.model.dto.TokensDTO;
import com.example.user_module.model.dto.UserDTO;
import com.example.user_module.model.dto.VerificationEmailDTO;
import com.example.user_module.model.request.LoginForm;
import com.example.user_module.model.request.SignUpForm;
import com.example.user_module.model.request.UpdateEmailInfoForm;
import com.example.user_module.template.EmailTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;
    private final VerificationService verificationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailSenderService senderService;


    @Value("${emailTemplate.verificationLink}")
    private String verificationLink;


    @Transactional
    public TokensDTO signUp(SignUpForm request) {
        UserDTO registeredUser = userService.registerUser(request);
        UUID code = verificationService.createCodeAndSave(registeredUser);
        sendCode(registeredUser, code);
        return tokenService.createTokens(registeredUser);
    }

    @Transactional
    public void resendCode(UpdateEmailInfoForm request, UserDTO user) {
        UserDTO userDTO=userService.updateEmail(user, request);
        UUID code = verificationService.createCodeAndSave(userDTO);
        sendCode(userDTO, code);
    }

    @Async
    void sendCode(UserDTO user, UUID code) {
        senderService.sendTemplate(
                user.getEmail(),
                "Подтверждение регистрации  в мессенджере Relex",
                EmailTemplate.VERIFICATION_USER,
                new VerificationEmailDTO(user.getFirstName() + " " + user.getSecondName()+" "+user.getSurname(),
                        verificationLink + "/" + code.toString()));
    }
    @Transactional
    public void resendCode(UserDTO user) {
        UUID code = verificationService.resendCode(user);
        sendCode(user, code);
    }

    @Transactional
    public void verifyUser(String code) {
        Long userId = verificationService.getVerificationUserId(code);
        userService.verifyUserById(userId);
    }
    @Transactional
    public TokensDTO login(LoginForm request) {
        if(userService.getUserByLogin(request.getLogin())==null){
            throw new WrongCredentialsException("Wrong login");
        }
        UserDTO user = userService.getUserByLogin(request.getLogin());
        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new WrongInputLoginException("wrong password");
        }
        return tokenService.createTokens(user);
    }

}
