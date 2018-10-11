package kr.pravusid.service;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JwtUserService {

    public boolean isValidUser(String accessToken, String username) {
        return getTokenUsername(accessToken).equals(username);
    }

    public String getTokenUsername(String accessToken) {
        Jwt jwt = getParsedToken(accessToken);
        Map claim = JsonParserFactory.create().parseMap(jwt.getClaims());
        return (String) claim.get("user_name");
    }

    private Jwt getParsedToken(String accessToken) {
        return JwtHelper.decode(accessToken.split(" ")[1]);
    }

}
