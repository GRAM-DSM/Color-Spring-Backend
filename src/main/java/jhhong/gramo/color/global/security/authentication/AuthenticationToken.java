package jhhong.gramo.color.global.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private final String email;
    private final String decodedToken;

    public AuthenticationToken(String email, String decodedToken) {
        super(null);
        super.setAuthenticated(true);
        this.email = email;
        this.decodedToken = decodedToken;
    }

    @Override
    public Object getCredentials() {
        return decodedToken;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }
}
