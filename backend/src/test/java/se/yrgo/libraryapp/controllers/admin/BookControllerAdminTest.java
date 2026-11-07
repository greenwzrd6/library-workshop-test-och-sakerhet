package se.yrgo.libraryapp.controllers.admin;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.*;

import se.yrgo.libraryapp.dao.*;
import se.yrgo.libraryapp.entities.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class BookControllerAdminTest {
    @Mock
    private BookDao bookDao;

    private BookController controller;

    @BeforeEach
    void setUp() {
        controller = new BookController(bookDao);
    }

    @Test
    void searchExistingBook() {
        final String id = "1";
        final BookId bookId = BookId.of(id);
        final BookEdition bookEdition = new BookEdition("Tjenare", "Benare", "9781509302000");
        final Book book = new Book(bookId, true, bookEdition);

        when(bookDao.get(bookId)).thenReturn(Optional.of(book));

        Book maybeBook = controller.search(id);

        assertThat(maybeBook).isEqualTo(book);
    }

    @Test
    void searchNonExistingBook() {
        final String id = "1";

        Book maybeBook = controller.search(id);

        assertThat(maybeBook).isNull();
    }
}
