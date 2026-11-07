package se.yrgo.libraryapp.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import com.radcortez.flyway.test.annotation.H2;

import se.yrgo.libraryapp.entities.*;

@Tag("integration")
@H2
public class BookDaoIntegrationTest {
    private static DataSource ds;

    @BeforeAll
    static void initDataSource() {
        // this way we do not need to create a new datasource every time
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        BookDaoIntegrationTest.ds = ds;
    }

    @Test
    void getByBookId() {
        final BookId bookId = BookId.of(1);
        BookDao bookDao = new BookDao(ds);

        Optional<Book> maybeBook = bookDao.get(bookId);

        assertThat(maybeBook).isPresent();
        assertThat(maybeBook.get().getId()).isEqualTo(bookId);
    }

    @Test
    void getByWrongId() {
        final BookId bookId = BookId.of(112312312);
        BookDao bookDao = new BookDao(ds);

        Optional<Book> maybeBook = bookDao.get(bookId);

        assertThat(maybeBook).isNotPresent();
    }

    @Test
    void findByEdition() {
        final BookEdition book = new BookEdition(
                3,
                "Här kommer Pippi Långstrump",
                "Astrid Lindgren",
                "9789129697285",
                new DdsClassification(898, "Scandinavian"));
        BookDao bookDao = new BookDao(ds);

        Set<BookEdition> books = bookDao.find(book.getIsbn(), book.getTitle(), book.getAuthor());

        assertThat(books).contains(book);
    }

    @Test
    void findHandlesNoBooks() {
        BookDao bookDao = new BookDao(ds);

        Set<BookEdition> books = bookDao.find("1231231231", "asdasd", "Erik Erik");

        assertThat(books).isEmpty();
    }

    @Test
    void withClassReturnsCorrectBooks() {
        final DdsClassification dds = new DdsClassification(898, "Scandinavian");
        final BookEdition book = new BookEdition(
                "Här kommer Pippi Långstrump",
                "Astrid Lindgren",
                "9789129697285");
        BookDao bookDao = new BookDao(ds);

        Set<BookEdition> books = bookDao.withClass(dds);

        assertThat(books).contains(book);
    }

    @Test
    void withClassIncorrectClassReturnsNoBooks() {
        final DdsClassification dds = new DdsClassification(123, "Test");
        BookDao bookDao = new BookDao(ds);

        Set<BookEdition> books = bookDao.withClass(dds);

        assertThat(books).isEmpty();
    }

}
