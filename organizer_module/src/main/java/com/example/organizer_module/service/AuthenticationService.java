package com.example.organizer_module.service;


import com.example.organizer_module.exception.*;
import com.example.organizer_module.model.dto.*;
import com.example.organizer_module.model.request.*;
import com.example.organizer_module.template.EmailTemplate;
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
    private final OrganizerService organizerService;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;
    private final VerificationService verificationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailSenderService senderService;


    @Value("${emailTemplate.verificationLink}")
    private String verificationLink;


    @Transactional
    public TokensDTO signUp(SignUpForm request) {
        OrganizerDTO registeredUser = organizerService.registerUser(request);
        UUID code = verificationService.createCodeAndSave(registeredUser);
        sendCode(registeredUser, code);
        return tokenService.createTokens(registeredUser);
    }

    @Transactional
    public void resendCode(UpdateEmailRequest request, OrganizerDTO user) {
        OrganizerDTO userDTO=organizerService.updateEmail(user, request);
        UUID code = verificationService.createCodeAndSave(userDTO);
        sendCode(userDTO, code);
    }

    @Async
    void sendCode(OrganizerDTO user, UUID code) {
        senderService.sendTemplate(
                user.getEmail(),
                "Подтверждение регистрации  в  ticket seller",
                EmailTemplate.VERIFICATION_ORGANIZER,
                new VerificationEmailDTO(user.getFirstName() + " " + user.getSecondName()+" "+user.getSurname(),
                        verificationLink + "/" + code.toString()));
    }
    @Transactional
    public void resendCode(OrganizerDTO user) {
        UUID code = verificationService.resendCode(user);
        sendCode(user, code);
    }

    @Transactional
    public void verifyUser(String code) {
        Long userId = verificationService.getVerificationUserId(code);
        organizerService.verifyUserById(userId);
    }
    @Transactional
    public TokensDTO login(LoginForm request) {
        if(organizerService.getOrganizerByLogin(request.getLogin())==null){
            throw new WrongCredentialsException("Wrong login");
        }
        OrganizerDTO user = organizerService.getOrganizerByLogin(request.getLogin());
        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new WrongInputLoginException("wrong password");
        }
        return tokenService.createTokens(user);
    }

}
