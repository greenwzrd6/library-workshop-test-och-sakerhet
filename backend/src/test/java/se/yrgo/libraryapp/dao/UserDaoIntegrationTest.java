package se.yrgo.libraryapp.dao;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.radcortez.flyway.test.annotation.H2;

import se.yrgo.libraryapp.entities.*;

@Tag("integration")
@H2
public class UserDaoIntegrationTest {
    private static DataSource ds;

    @BeforeAll
    static void initDataSource() {
        // this way we do not need to create a new datasource every time
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        UserDaoIntegrationTest.ds = ds;
    }

    @Test
    void getUserById() {
        // this data comes from the test migration files
        final String username = "test";
        final UserId userId = UserId.of(1);
        UserDao userDao = new UserDao(ds);
        Optional<User> maybeUser = userDao.get(Integer.toString(userId.getId()));
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.get().getName()).isEqualTo(username);
        assertThat(maybeUser.get().getId()).isEqualTo(userId);
    }

    @Test
    void getUserByNonExistingId() {
        UserDao userDao = new UserDao(ds);

        Optional<User> maybeUser = userDao.get(Integer.toString(UserId.of(42).getId()));
        assertThat(maybeUser).isNotPresent();
    }

    @Test
    void getLoginInformationByUser() {
        final String userName = "test";
        final UserId userId = UserId.of(1);
        final String passwordHash = "$argon2i$v=19$m=16,t=2,p=1$MTIzNDU2Nzg5MDEyMzQ1NjA$LmFqTZeUWwqsnbZCS2E8XQ";
        UserDao userDao = new UserDao(ds);

        Optional<LoginInfo> maybeLogin = userDao.getLoginInfo(userName);
        assertThat(maybeLogin).isPresent();
        assertThat(maybeLogin.get().getUserId()).isEqualTo(userId);
        assertThat(maybeLogin.get().getPasswordHash()).isEqualTo(passwordHash);
    }

    @Test
    void getLoginInformationByInvalidUser() {
        final String userName = "smesk";
        UserDao userDao = new UserDao(ds);

        Optional<LoginInfo> maybeLogin = userDao.getLoginInfo(userName);
        assertThat(maybeLogin).isNotPresent();
    }

    @Test
    void isNameAvailableWithValidName() {
        final String name = "fungerandenamn";
        UserDao userDao = new UserDao(ds);

        assertThat(userDao.isNameAvailable(name)).isTrue();
    }

    @Test
    void isNameAvailableWithInvalidName() {
        final String name = "test";
        UserDao userDao = new UserDao(ds);

        assertThat(userDao.isNameAvailable(name)).isFalse();
    }

    @Test
    void registerUser() {
        final String name = "david";
        final String realname = "David S'mavid";
        final String passwordHash = "$argon2i$v=19$m=16,t=2,p=1$ZmxWQTJrTmtpUlBEZFZaWA$9+AJzL7CHgQGdOgkX7CiLw";
        UserDao userDao = new UserDao(ds);

        assertThat(userDao.register(name, realname, passwordHash)).isTrue();
        assertThat(userDao.get("3").get().getName()).isEqualTo(name);
        assertThat(userDao.get("3").get().getRealname()).isEqualTo(realname);
        assertThat(userDao.getLoginInfo(name).get().getPasswordHash()).isEqualTo(passwordHash);
    }
}