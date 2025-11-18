package kz.stech.teachback.security.internal.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {

    private static final String SECRET_TOKEN = "LSDKJGFJHSFGUJHSFFIWEFUHRIFSDJFHNSJKDHGJKSFGHSDKJFGHKSDJFHIWUHFSIKDHJKJXZHFJKSDHFKJSDHGFKJSDFGBKJSDFHJKSDFHJKSDFH";


    public Jwt parseJwt(String token) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(
                new SecretKeySpec(SECRET_TOKEN.getBytes(), "HmacSHA256")
        ).build();
        return decoder.decode(token);
    }

    private String generateToken(UUID userId, String username, Set<String> roles, Date expiration) {
        try {
            JWSSigner signer = new MACSigner(SECRET_TOKEN);

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(String.valueOf(userId))
                    .claim("username", username)
                    .claim("roles", roles)
                    .issueTime(new Date())
                    .expirationTime(expiration)
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при генерации JWT", e);
        }
    }

    public String generateToken(UUID userId, String username, Set<String> roles) {
        return this.generateToken(userId, username, roles, Date.from(Instant.now().plusSeconds(3600)));
    }


    public String generateRefreshToken(UUID userId, String username, Set<String> roles) {
        return this.generateToken(userId, username, roles, Date.from(Instant.now().plusSeconds(604800)));
    }


    public boolean validateToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET_TOKEN);

            return jwt.verify(verifier) && jwt.getJWTClaimsSet().getExpirationTime().after(new Date());

        } catch (Exception e) {
            return false;
        }
    }

    public UUID getUserIdFromToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            return UUID.fromString(jwt.getJWTClaimsSet().getSubject());
        } catch (Exception e) {
            return null;
        }
    }
}
