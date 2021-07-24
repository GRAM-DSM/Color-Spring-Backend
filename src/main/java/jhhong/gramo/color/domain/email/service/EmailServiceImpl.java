package jhhong.gramo.color.domain.email.service;

import jhhong.gramo.color.domain.email.entity.EmailUser;
import jhhong.gramo.color.domain.email.entity.EmailUserRepository;
import jhhong.gramo.color.domain.email.exceptions.NumberNotFoundException;
import jhhong.gramo.color.domain.email.payload.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private static final Random random = new Random();
    private final JavaMailSender javaMailSender;
    private final EmailUserRepository emailUserRepository;

    @Override
    public Mono<Void> sendEmail(EmailRequest request) {
        return Mono.fromRunnable(() -> {
            String code = getRandomHexString().toUpperCase();

            javaMailSender.send(mimeMessage -> {
                final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setFrom("201420hjh@dsm.hs.kr");
                helper.setTo(request.email());
                helper.setSubject("#Color 인증 코드");
                helper.setText(code);
            });

            emailUserRepository.findByEmail(request.email())
                    .flatMap(emailUser -> Mono.just(emailUser.update(code)))
                    .flatMap(emailUserRepository::save)
                    .switchIfEmpty(emailUserRepository.save(
                            EmailUser.builder()
                                    .email(request.email())
                                    .code(code)
                                    .build()
                    )).subscribe();
        }).publishOn(Schedulers.parallel()).then();
    }

    @Override
    public Mono<Void> verifyUser(String email, String code) {
        return emailUserRepository.findByEmailAndCode(email, code.toUpperCase())
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
