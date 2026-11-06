package se.yrgo.libraryapp.services;

import java.util.Optional;
import javax.inject.Inject;

import org.slf4j.*;
import org.springframework.security.crypto.password.*;

import se.yrgo.libraryapp.dao.UserDao;
import se.yrgo.libraryapp.entities.*;

public class UserService {
    private UserDao userDao;
    private PasswordEncoder encoder;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserService(UserDao userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    public Optional<UserId> validate(String username, String password) {
        Optional<LoginInfo> maybeLoginInfo = userDao.getLoginInfo(username);
        if (maybeLoginInfo.isEmpty()) {
            return Optional.empty();
        }

        LoginInfo loginInfo = maybeLoginInfo.get();

        if (!encoder.matches(password, loginInfo.getPasswordHash())) {
            return Optional.empty();
        }

        return Optional.of(loginInfo.getUserId());
    }

    public boolean isNameAvailable(String name) {
        if (name == null || name.trim().length() < 3) {
            return false;
        }
        return userDao.isNameAvailable(name);
    }

    public boolean register(String name, String realname, String password) {
        String passwordHash = encoder.encode(password);

        if (!isNameAvailable(name)) {
            logger.error("\"{}\" is not an available name", name);
            return false;
        }

        return userDao.register(name, realname, passwordHash);
    }
}
