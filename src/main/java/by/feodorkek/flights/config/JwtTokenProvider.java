package by.feodorkek.flights.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * This {@link Component @Component} requires for generating and validating authentication user token
 */
@Component
public class JwtTokenProvider {

    /**
     * Application config string secret key value which used to encrypt information,
     * placed in a token to prevent unauthorized reading
     * <p>
     * Note:
     * Secret key creates a digital signature that confirms the authenticity of the token
     * and protects it from counterfeiting. When validating a token, the private key is used
     * to confirm that the data has not been modified since it was created
     */
    @Value("${security.jwt-secret}")
    private String secret;

    /**
     * Application config long value - time in milliseconds after which the token
     * becomes invalid and needs to be updated
     */
    @Getter
    @Value("${security.jwt-expiration-time-ms}")
    private long expirationTime;

    /**
     * Generate an authorization token based on the provided {@link Authentication} information
     *
     * @param authentication user {@link Authentication} from new {@link UsernamePasswordAuthenticationToken}
     *                       or current user authentication from {@link SecurityContextHolder}
     * @return generated token string
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        // Getting current server time for creating new expire time
        Date currentDate = new Date();
        // Creating new expire time for token
        Date expireDate = new Date(currentDate.getTime() + expirationTime);
        return Jwts.builder()
                .subject(username)
                // Sets the current time as current release time
                .issuedAt(currentDate)
                // Sets the token expiration time
                .expiration(expireDate)
                // Signs a token using a private key (can throw InvalidKeyException)
                .signWith(getSecret())
                // Completes the creation of the token and returns it as a string
                .compact();
    }

    /**
     * Verify the authenticity and integrity of the token
     *
     * @param token input string token value
     * @return if the token successfully passed verification, the method returns true
     * Otherwise, the method will throw an exception
     */
    public boolean validateToken(String token) {
        Jwts.parser()
                // Configures the parser to use the secret key to verify the token signature
                .verifyWith(getSecret())
                .build()
                /* Parses and verifies the token using a previously configured parser.
                If the token is invalid or the signature does not match, an exception will be thrown.
                Can throw exceptions: ExpiredJwtException, MalformedJwtException, SignatureException,
                                      SecurityException, IllegalArgumentException */
                .parse(token);
        return true;
    }

    /**
     * Obtain a {@link SecretKey}, which is used for signing and verification
     * <p>
     * The method uses {@code Decoders.BASE64.decode(secret)} to decode the secret string,
     * which is encoded in {@link Base64} format. This converts the string to a byte array
     * <p>
     * Method {@code Keys.hmacShaKeyFor} takes a byte array and creates a SecretKey object from it,
     * which is used for the {@code HMAC-SHA} algorithm
     *
     * @return generated {@link SecretKey}
     */
    private SecretKey getSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * Extract the username from the token
     *
     * @param token Input string token
     * @return Username extracted from token if token parsing was successful
     */
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSecret())
                .build()
                // Parses the token and extracts signed statements from it.
                // Can throw JwtException, IllegalArgumentException
                .parseSignedClaims(token)
                // Extracts the payload from the token
                .getPayload()
                // Extracts and returns the subject value, which typically contains the username
                .getSubject();
    }

}