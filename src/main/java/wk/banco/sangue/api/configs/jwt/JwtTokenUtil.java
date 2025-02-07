package wk.banco.sangue.api.configs.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import wk.banco.sangue.api.domain.dtos.CustomUserDetails;

@Component
public class JwtTokenUtil {

    private final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60 * 10;

    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", customUserDetails.getName());
        userInfo.put("email", customUserDetails.getUsername());
        userInfo.put("tipo_sanguineo", customUserDetails.getTipoSanguineo());

        claims.put("userInfo", userInfo);

        return createToken(claims, customUserDetails.getName(), ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", customUserDetails.getName());
        userInfo.put("email", customUserDetails.getUsername());

        claims.put("userInfo", userInfo);

        return createToken(claims, customUserDetails.getName(), REFRESH_TOKEN_EXPIRATION);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
