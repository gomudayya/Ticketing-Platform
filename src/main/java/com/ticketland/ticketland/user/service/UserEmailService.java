package com.ticketland.ticketland.user.service;

import com.ticketland.ticketland.user.domain.JoinVerification;
import com.ticketland.ticketland.user.exception.InvalidVerifyException;
import com.ticketland.ticketland.user.exception.VerifyExpiredException;
import com.ticketland.ticketland.user.repository.JoinVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UserEmailService {

    @Value("${spring.mail.username}")
    private String EMAIL_SENDER;

    private final MailSender mailSender;

    private final JoinVerificationRepository joinVerificationRepository;


    public void sendVerificationCode(String email) {
        int codeNumber = ThreadLocalRandom.current().nextInt(100_000, 1_000_000); // 6자리 인증 번호 생성

        SimpleMailMessage mail =new SimpleMailMessage();
        mail.setFrom(EMAIL_SENDER);
        mail.setTo(email);
        mail.setSubject("[티켓랜드] 이메일 인증번호 입니다.");
        mail.setText(String.format("이메일 인증번호는 %d 입니다.", codeNumber));

        mailSender.send(mail);

        JoinVerification joinVerification = new JoinVerification(email, codeNumber);
        joinVerificationRepository.save(joinVerification);
    }

    public void verifyEmail(String email, int verifyCode) {
        JoinVerification joinVerification = joinVerificationRepository.findById(email)
                .orElseThrow(VerifyExpiredException::new);

        if (!joinVerification.codeEquals(verifyCode)) {
            throw new InvalidVerifyException(); // 인증번호가 안맞으면 예외
        }

        joinVerification.setVerified();
        joinVerificationRepository.save(joinVerification);
    }
}
