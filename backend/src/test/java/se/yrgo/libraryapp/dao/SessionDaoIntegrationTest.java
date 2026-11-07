package se.yrgo.libraryapp.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import javax.sql.DataSource;

import org.h2.jdbcx.*;
import org.junit.jupiter.api.*;
import org.pac4j.core.exception.*;

import com.radcortez.flyway.test.annotation.*;

import se.yrgo.libraryapp.entities.*;

@Tag("integration")
@H2
public class SessionDaoIntegrationTest {
    private static DataSource ds;

    @BeforeAll
    static void initDataSource() {
        // this way we do not need to create a new datasource every time
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        SessionDaoIntegrationTest.ds = ds;
    }

    @Test
    void createWithUserIdReturnsSessionIdForCorrectUser() {
        final UserId userId = UserId.of(1);
        SessionDao sessionDao = new SessionDao(ds);

        UUID sessionId = sessionDao.create(userId);

        assertThat(sessionId).isNotNull();
        assertThat(sessionDao.validate(sessionId)).isEqualTo(userId);
    }

    @Test
    void createWithNonExistingUserThrows() {
        UserId nonExistingUser = UserId.of(10000);
        SessionDao sessionDao = new SessionDao(ds);

        assertThatThrownBy(() -> sessionDao.create(nonExistingUser))
                .isInstanceOf(CredentialsException.class)
                .hasMessageContaining("Unable to create session");
    }
}
