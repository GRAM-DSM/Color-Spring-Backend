package jhhong.gramo.color.domain.user.service;

import jhhong.gramo.color.domain.email.entity.EmailUser;
import jhhong.gramo.color.domain.email.entity.EmailUserExportRepository;
import jhhong.gramo.color.domain.user.entity.User;
import jhhong.gramo.color.domain.user.entity.UserRepository;
import jhhong.gramo.color.domain.user.exception.EmailUserNotFoundException;
import jhhong.gramo.color.domain.user.exception.UserAlreadyExistsException;
import jhhong.gramo.color.domain.user.payload.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailUserExportRepository emailUserExportRepository;

    @Override
    public Mono<Void> createUser(UserRequest request) {
        Mono<EmailUser> emailUserMono = userRepository.existsByEmail(request.email())
                .filter(bool -> !bool)
                .switchIfEmpty(Mono.error(UserAlreadyExistsException::new))
                .flatMap(bool -> emailUserExportRepository.findByEmail(request.email()));

        return emailUserMono
                .flatMap(emailUser -> userRepository.save(this.buildUser(request)))
                .switchIfEmpty(Mono.error(EmailUserNotFoundException::new))
                .then();
    }

    private User buildUser(UserRequest request) {
        return User.builder()
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .email(request.email())
                .build();
    }
}
