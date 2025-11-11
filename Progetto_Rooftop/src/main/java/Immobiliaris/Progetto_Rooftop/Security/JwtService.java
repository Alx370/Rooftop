package Immobiliaris.Progetto_Rooftop.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    // The algorithm used to sign and verify tokens
    private final Algorithm alg;

    // Identifier of who issues the token
    private final String issuer;

    private final long expMinutes;

    // The constructor injects values from application.properties:
    public JwtService(
        @Value("${app.jwt.secret}") String secret,
        @Value("${app.jwt.issuer}") String issuer,
        @Value("${app.jwt.exp-minutes}") long expMinutes
    ) {
        // Configure the signing algorithm with the secret key
        this.alg = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.expMinutes = expMinutes;
    }

    /**
     * Generates a new JWT token.
     * @param subject the main identity of the user (e.g., user ID or email)
     * @param claims  additional custom data to include in the payload (e.g., role, email)
     * @return a signed JWT string
     */
    public String generateToken(String subject, Map<String, String> claims) {
        // Capture the current timestamp
        Instant now = Instant.now();

        // Start building the JWT structure
        var builder = JWT.create()
                .withIssuer(issuer)
                // Date when the token was issued
                .withIssuedAt(Date.from(now))
                // Expiration date
                .withExpiresAt(Date.from(now.plus(expMinutes, ChronoUnit.MINUTES))) // Chrono unit is a class that helps manipulate time intervals
                // The subject
                .withSubject(subject);

        // Add custom claims (like role, email)
        if (claims != null) {
            claims.forEach(builder::withClaim);
        }

        // sign the token with the algorithm
        return builder.sign(alg);
    }

    // Verifies and decodes a JWT token.
    public DecodedJWT verify(String token) {
        // Create a verifier using the same algorithm and issuer, then verify the token
        return JWT.require(alg).withIssuer(issuer).build().verify(token);
    }
}
