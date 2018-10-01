package kr.pravusid.domain.user.persistentlogins;

import kr.pravusid.domain.user.User;
import kr.pravusid.domain.user.UserRepository;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class PersistentTokenRepositoryImpl implements PersistentTokenRepository {

    private PersistentLoginsRepository persistentLoginsRepository;
    private UserRepository userRepository;

    public PersistentTokenRepositoryImpl(PersistentLoginsRepository persistentLoginsRepository,
                                         UserRepository userRepository) {
        this.persistentLoginsRepository = persistentLoginsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        User user = userRepository.findByUsername(token.getUsername());
        PersistentLogins persistentLogins = new PersistentLogins(user, token.getSeries(), token.getTokenValue(),
                dateToLocalDate(token.getDate()));
        persistentLoginsRepository.save(persistentLogins);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentLogins persistentLogins = persistentLoginsRepository.findOne(series);
        persistentLogins.update(series, tokenValue, dateToLocalDate(lastUsed));
        persistentLoginsRepository.save(persistentLogins);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
        PersistentLogins persistentLogins = persistentLoginsRepository.findOne(series);
        try {
            return new PersistentRememberMeToken(persistentLogins.getUser().getUsername(), series,
                    persistentLogins.getToken(), localDateToDate(persistentLogins.getLastUsed()));
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public void removeUserTokens(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            persistentLoginsRepository.deleteByUserId(user.getId());
        }
    }

    private LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
