package com.solarize.solarizeWebBackend.modules.auth;

import com.solarize.solarizeWebBackend.modules.auth.dtos.CoworkerDetailsDto;
import com.solarize.solarizeWebBackend.modules.auth.dtos.RecoveryPasswordOtpDto;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.modules.auth.dtos.AuthResponseDto;
import com.solarize.solarizeWebBackend.shared.caffeine.OTPCacheManager;
import com.solarize.solarizeWebBackend.shared.caffeine.RecoveryPasswordTokenCacheManager;
import com.solarize.solarizeWebBackend.shared.email.EmailService;
import com.solarize.solarizeWebBackend.shared.email.EmailTemplateProcessor;
import com.solarize.solarizeWebBackend.shared.email.model.PasswordRecoveryEmail;
import com.solarize.solarizeWebBackend.shared.security.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final CoworkerRepository coworkerRepository;
    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationConfiguration authenticationManager;
    private final OTPCacheManager otpCacheManager;
    private final RecoveryPasswordTokenCacheManager tokenCacheManager;
    private final EmailService emailService;
    private final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();


    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Coworker> usuarioOpt = coworkerRepository.findByEmailWithPermissions(username);

        if (usuarioOpt.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        return new CoworkerDetailsDto(usuarioOpt.get());
    }


    public AuthResponseDto login(Coworker coworker){

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(coworker.getEmail(), coworker.getPassword());

        try {
            final Authentication authentication = this.authenticationManager.getAuthenticationManager().authenticate(credentials);

            Coworker coworkerAuthenticated = coworkerRepository.findByEmail(coworker.getEmail()).orElseThrow(()-> new ResponseStatusException(404, "Email do usuário não cadastrado", null));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final String token =  jwtTokenManager.generateToken(authentication, coworkerAuthenticated.getId());

            return AuthMapper.of(coworkerAuthenticated, token, authentication.getAuthorities().stream().collect(Collectors.toList()));
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    public void requestRecoveryPasswordCode(String email, String browser, String operationalSystem, String ip) {
        Optional<Coworker> coworker = coworkerRepository.findByEmail(email);

        if(coworker.isPresent()) {
            String otpCode = String.format("%06d", secureRandom.nextInt(1_000_000));

            Coworker user = coworker.get();

            this.otpCacheManager.saveCache(user.getEmail(), otpCode);

            PasswordRecoveryEmail emailModel = PasswordRecoveryEmail.builder()
                    .to(email)
                    .subject("Recuperação de Senha Solarize")
                    .name(user.getFirstName())
                    .code(otpCode)
                    .operatingSystem(operationalSystem)
                    .browser(browser)
                    .ip(ip)
                    .build();

            String template = EmailTemplateProcessor.buildTemplate(emailModel);

            this.emailService.sendEmail(email, "Recuperação de senha Solarize", template);
        }
    }

    public String confirmOtpCode(String email, String otp) {
        String cachedOtp = this.otpCacheManager.getCache(email);

        if(cachedOtp == null || !cachedOtp.equals(otp)) {
            throw new BadCredentialsException("Invalid Code");
        }

        byte[] bytes = new byte[64];
        secureRandom.nextBytes(bytes);

        String token = base64Encoder.encodeToString(bytes);

        tokenCacheManager.saveCache(token, email);
        return token;
    }
}
