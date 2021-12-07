package com.demo.config.security.jwt;

import com.demo.config.constant.SecurityConst;
import com.demo.bean.UserDetail;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Alex
 * @date 2021/9/18 14:03
 */
@Slf4j
public class JwtTokenProvider {

    public static String createJwtToken(Authentication authentication) {

        //user name

        String username = ((UserDetail)authentication.getPrincipal()).getUsername();

        //expire time

        Date expireTime = new Date(System.currentTimeMillis() + SecurityConst.TOKEN_EXPIRED);

        //create token

        return Jwts.builder()

                .setSubject(username)

                .setExpiration(expireTime)

                .setIssuedAt(new Date())

                .signWith(SignatureAlgorithm.HS512, SecurityConst.TOKEN_SECRET_KEY)

                .compact();

    }

    public static String getJwtFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            return token.replace("Bearer ", "");
        }
        return null;
    }

    public static String getUsernameFromJwt(String token) {
        return Jwts.parser().setSigningKey(SecurityConst.TOKEN_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean validateToken(String token) {

        try {
            Jwts.parser()
                    .setSigningKey(SecurityConst.TOKEN_SECRET_KEY)
                    .parseClaimsJws(token);

            return true;

        }catch (Exception ex) {

            log.error("Token Validate Failed: " + ex.getMessage());

            return false;

        }

    }



}
