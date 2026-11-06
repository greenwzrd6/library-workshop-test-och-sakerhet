package se.yrgo.libraryapp.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import javax.sql.*;
import javax.sql.DataSource;

import org.bouncycastle.crypto.encodings.*;
import org.h2.jdbcx.*;
import org.junit.jupiter.api.*;

import com.radcortez.flyway.test.annotation.*;

import se.yrgo.libraryapp.entities.*;

@Tag("integration")
@H2
public class RoleDaoIntegrationTest {
    private static DataSource ds;

    @BeforeAll
    static void initDataSource() {
        // this way we do not need to create a new datasource every time
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        RoleDaoIntegrationTest.ds = ds;
    }

    @Test
    void getByUserId() {
        final UserId userId = UserId.of(1);
        RoleDao roleDao = new RoleDao(ds);

        List<Role> roles = roleDao.get(userId);

        assertThat(roles).containsExactly(Role.ADMIN);
    }

    @Test
    void getByInvalidUserId() {
        final UserId userId = UserId.of(60);
        RoleDao roleDao = new RoleDao(ds);

        List<Role> roles = roleDao.get(userId);

        assertThat(roles).isEmpty();
    }
}
