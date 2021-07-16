package jhhong.gramo.color.domain.email.service;

import jhhong.gramo.color.domain.email.exceptions.EmailSendFailException;
import jhhong.gramo.color.domain.email.payload.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private static final Random random = new Random();
    private final ReactiveRedisOperations<String, String> redisOperations;
    private final JavaMailSender javaMailSender;

    @Override
    public Mono<Void> sendEmail(EmailRequest request) {
        return Mono.fromCallable(() -> {
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

                redisOperations
                        .opsForValue().set(code, request.email());

                return true;

            } catch (Exception e) {
                return Mono.error(new EmailSendFailException());
            }
        }).then();
    }

    private String getRandomHexString() {
        StringBuffer sb = new StringBuffer();
         while (sb.length() < 6) {
            sb.append(Integer.toHexString(random.nextInt()));
        }
        return sb.toString().substring(0, 6);
    }

}
