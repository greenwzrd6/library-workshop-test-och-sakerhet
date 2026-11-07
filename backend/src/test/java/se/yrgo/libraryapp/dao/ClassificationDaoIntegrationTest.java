package se.yrgo.libraryapp.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import javax.sql.DataSource;

import org.h2.jdbcx.*;
import org.junit.jupiter.api.*;

import com.radcortez.flyway.test.annotation.*;

import se.yrgo.libraryapp.entities.*;

@Tag("integration")
@H2
public class ClassificationDaoIntegrationTest {
    private static DataSource ds;

    @BeforeAll
    static void initDataSource() {
        // this way we do not need to create a new datasource every time
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        ClassificationDaoIntegrationTest.ds = ds;
    }

    @Test
    void getAvailableReturns() {
        ClassificationDao classificationDao = new ClassificationDao(ds);

        List<DdsClassification> availableDds = classificationDao.getAvailable();

        assertThat(availableDds)
                .extracting(dds -> dds.getCode())
                .containsExactlyInAnyOrder(0, 5, 306, 510, 641, 800, 898);
    }
}
