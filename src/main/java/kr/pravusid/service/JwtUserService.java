package kr.pravusid.service;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JwtUserService {

    public boolean isValidUser(String requestToken, String username) {
        return getTokenUsername(requestToken).equals(username);
    }

    public String getTokenUsername(String requestToken) {
        Jwt jwt = getParsedToken(requestToken);
        Map claim = JsonParserFactory.create().parseMap(jwt.getClaims());
        return (String) claim.get("user_name");
    }

    private Jwt getParsedToken(String requestToken){
        return JwtHelper.decode(requestToken.split(" ")[1]);
    }

}
