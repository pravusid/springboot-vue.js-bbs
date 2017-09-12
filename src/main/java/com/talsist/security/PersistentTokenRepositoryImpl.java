package com.talsist.security;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.talsist.domain.PersistentLogins;
import com.talsist.domain.User;
import com.talsist.repository.PersistentLoginsRepository;
import com.talsist.repository.UserRepository;

public class PersistentTokenRepositoryImpl implements PersistentTokenRepository {

    @Autowired
    private PersistentLoginsRepository persistentLoginsRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        User user = userRepository.findByUserId(token.getUsername());
        PersistentLogins persistentLogins =
                new PersistentLogins(user, token.getSeries(), token.getTokenValue(), token.getDate());
        
        persistentLoginsRepository.save(persistentLogins);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentLogins persistentLogins = persistentLoginsRepository.findOne(series);
        persistentLogins.update(series, tokenValue, lastUsed);
        persistentLoginsRepository.save(persistentLogins);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
        PersistentLogins persistentLogins = persistentLoginsRepository.findOne(series);
        return new PersistentRememberMeToken(persistentLogins.getUser().getUserId(), series,
                persistentLogins.getToken(), persistentLogins.getLastUsed());
    }

    @Transactional
    @Override
    public void removeUserTokens(String username) {
        User user = userRepository.findByUserId(username);
        persistentLoginsRepository.deleteByUserId(user.getId());
    }
    
}
