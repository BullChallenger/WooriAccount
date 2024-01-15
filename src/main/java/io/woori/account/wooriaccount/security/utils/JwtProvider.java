package io.woori.account.wooriaccount.security.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProvider {


    //해당 jwtSecretKey는 실제 토큰이 아닌 JWT 생성, 검증에 사용되는 비밀키입니다. (서버에서 공유함)
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final UserDetailsService userDetailsService;
    private static final long ACCESS_TOKEN_EXPIRED_TIME = 1000L * 60 * 60; // 1시간
    private static final long REFRESH_TOKEN_EXPIRED_TIME = 1000L * 60L * 60L * 24L * 7L; // 7일

    /*
    * jwt를 생성하기 위해 사용되는 비밀키를 가져오는 메서드입니다.
    * HMAC-SHA 알고리즘에 따라서 키를 생성하고 생성된 키는 JWT 서명에 사용하여 토큰 발급과 검증에 사용합니다.
    * */

    private Key getSecretKey() {
        return SECRET_KEY;
    }


    public String createToken(String loginId,
                              List<String> roles,
                              long expiredTime) {
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("roles", roles);
        Date date = new Date();

        return Jwts.builder()
                .setSubject(loginId)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + expiredTime))
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }


    public String createAccessToken(String loginId, List<String> roles){
        return createToken(loginId, roles, ACCESS_TOKEN_EXPIRED_TIME);
    }

    public String createRefreshToken(String loginId, List<String> roles){
        return createToken(loginId, roles, REFRESH_TOKEN_EXPIRED_TIME);

    }

    public String extractLoginId(String token) {
        log.info("token : {}", token);
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date extractExpiredTime(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }


    public boolean isValidToken(String token) {
        try {

            log.info("token {}", token);
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);

        } catch (Exception e) {
            log.info("Invalid token. Reason: {}", e.getMessage());
            return false;
        }
        return true;
    }

//    public boolean isValidToken(String token) {
//        try {
//            Jws<Claims> claimsJws = Jwts.parserBuilder()
//                    .setSigningKey(getSecretKey())
//                    .build()
//                    .parseClaimsJws(token);
//
//        } catch (SignatureException e) {
//            log.error("SignatureException: {}", e.getMessage());
//            return false;
//        } catch (Exception e) {
//            log.error("Unexpected exception while parsing JWT token", e);
//            return false;
//        }
//        return true;
//    }


    /**
     * 엑세스 토큰을 재발급해줍니다.
     * */
    public String reissueToken(String loginId, List<String> roles) {
        return createAccessToken(loginId, roles);
    }

    public Authentication getAuthentication(String token){

        log.info("token : {}", token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(extractLoginId(token));
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );
    }



}
