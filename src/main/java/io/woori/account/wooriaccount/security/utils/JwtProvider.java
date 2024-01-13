package io.woori.account.wooriaccount.security.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
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
public class JwtProvider {


    //해당 jwtSecretKey는 실제 토큰이 아닌 JWT 생성, 검증에 사용되는 비밀키입니다. (서버에서 공유함)
    @Value("jwt.secretKey")
    private String jwtSecretKey;

    private final UserDetailsService userDetailsService;
    private static final long ACCESS_TOKEN_EXPIRED_TIME = 1000L * 60 * 60; // 1시간
    private static final long REFRESH_TOKEN_EXPIRED_TIME = 1000L * 60L * 60L * 24L * 7L; // 7일

    /*
    * jwt를 생성하기 위해 사용되는 비밀키를 가져오는 메서드입니다.
    * HMAC-SHA 알고리즘에 따라서 키를 생성하고 생성된 키는 JWT 서명에 사용하여 토큰 발급과 검증에 사용합니다.
    * */
    private Key getSecretKey(){
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String createToken(String loginId,
                              List<String> roles,
                              long expiredTime){

        //payload에 일부분을 나타냅니다. (payload : 내용) - 원하는 정보를 여기에 넣어줍니다.
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("roles", roles); //(payload)
        Date date = new Date();

        Key secretKey = getSecretKey();


        return Jwts.builder()
                .setSubject(loginId) // 토큰 사용자 id, 고유 식별자를 넣어 구분합니다.(payload)
                .setIssuedAt(date) // 토큰 발급 시간 설정합니다.
                .setExpiration(new Date(date.getTime() + expiredTime)) // 토큰 만료시간을 설정합니다.
                .signWith(secretKey, SignatureAlgorithm.HS512) // 해시 알고리즘 정보를 이용해서 토큰에 서명을 추가합니다. (header + signature)
                .compact();
    }


    public String createAccessToken(String loginId, List<String> roles){
        return createToken(loginId, roles, ACCESS_TOKEN_EXPIRED_TIME);
    }

    public String createRefreshToken(String loginId, List<String> roles){
        return createToken(loginId, roles, REFRESH_TOKEN_EXPIRED_TIME);

    }

    public String extractLoginId(String token) {
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


    public boolean isValidToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 엑세스 토큰을 재발급해줍니다.
     * */
    public String reissueToken(String loginId, List<String> roles) {
        return createAccessToken(loginId, roles);
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractLoginId(token));
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );
    }



}
