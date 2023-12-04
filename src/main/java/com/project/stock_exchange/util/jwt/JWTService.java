package com.project.stock_exchange.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secretKey}")
    private String secret;
    private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(5);

    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
        final Instant now = Instant.now();
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withIssuer("Aseem Sahoo")
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis())))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withSubject("User Details")
                    .withIssuer("Aseem Sahoo")
                    .build();
            DecodedJWT jwt = verifier.verify(token);

            // Check if the token is not expired
            if (jwt.getExpiresAt() != null && jwt.getExpiresAt().before(new Date())) {
                throw new JWTVerificationException("Token has expired");
            }

            // Retrieve and return the username
            return jwt.getClaim("username").asString();
        }
        catch(TokenExpiredException tokenExc)
        {
            throw new JWTVerificationException("The token has expired");
        }
        catch (JWTVerificationException jwtExc) {
            // Token verification failed
//            throw new TokenValidationException("Token verification failed");
            throw new JWTVerificationException("Token verification failed");
        }
    }

}