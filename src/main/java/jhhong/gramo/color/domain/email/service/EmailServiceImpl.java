package jhhong.gramo.color.domain.email.service;

import jhhong.gramo.color.domain.email.entity.EmailUser;
import jhhong.gramo.color.domain.email.entity.EmailUserRepository;
import jhhong.gramo.color.domain.email.exceptions.EmailSendFailException;
import jhhong.gramo.color.domain.email.exceptions.NumberNotFoundException;
import jhhong.gramo.color.domain.email.payload.EmailRequest;
import jhhong.gramo.color.domain.email.payload.VerifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private static final Random random = new Random();
    private final JavaMailSender javaMailSender;
    private final EmailUserRepository emailUserRepository;

    @Async
    @Override
    public void sendEmail(EmailRequest request) {
        Mono.fromCallable(() -> {
            try {
                String code = getRandomHexString().toUpperCase();
                final MimeMessagePreparator preparator = mimeMessage -> {
                    final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    helper.setFrom("201420hjh@dsm.hs.kr");
                    helper.setTo(request.email());
                    helper.setSubject("#Color 인증 코드");
                    helper.setText(code);
                };

                javaMailSender.send(preparator);

                return emailUserRepository.save(
                        EmailUser.builder()
                                .email(request.email())
                                .code(code)
                                .build()
                ).subscribe();

            } catch (Exception e) {
                return Mono.error(new EmailSendFailException());
            }
        }).subscribe();
    }

    @Override
    public Mono<Void> verifyUser(VerifyRequest request) {
        return emailUserRepository.findByEmailAndCode(request.email(), request.code())
                .flatMap(emailUser -> Mono.just(emailUser.updateStatus()))
                .flatMap(emailUserRepository::save)
                .switchIfEmpty(Mono.error(NumberNotFoundException::new))
                .then();
    }

    private String getRandomHexString() {
        StringBuffer sb = new StringBuffer();
        while (sb.length() < 6) {
            sb.append(Integer.toHexString(random.nextInt()));
        }
        return sb.toString().substring(0, 6);
    }

}
