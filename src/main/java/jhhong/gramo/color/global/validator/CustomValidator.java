package jhhong.gramo.color.global.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class CustomValidator {

    private final Validator validator;

    public <T> Mono<T> validate(T target) {
        Mono<Errors> errors = Mono.just(new BeanPropertyBindingResult(target, "target"));
        return errors.doOnNext(err -> validator.validate(target, err))
                .filter(err -> !err.hasErrors())
                .flatMap(err -> Mono.just(target))
                .switchIfEmpty(Mono.error(BadRequestException::new));
    }
}
