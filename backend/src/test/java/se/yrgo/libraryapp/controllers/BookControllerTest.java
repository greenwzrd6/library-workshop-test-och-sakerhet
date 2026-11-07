package se.yrgo.libraryapp.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.stream.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.*;

import se.yrgo.libraryapp.dao.*;
import se.yrgo.libraryapp.entities.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class BookControllerTest {
    @Mock
    private BookDao bookDao;

    private BookController controller;

    @BeforeEach
    void setUp() {
        controller = new BookController(bookDao);
    }

    static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of("9781509302000", null, null),
                Arguments.of(null, "Tjenare", null),
                Arguments.of(null, null, "Benare"),
                Arguments.of("9781509302000", "Tjenare", "Benare"));
    }

    @ParameterizedTest
    @MethodSource("testArguments")
    void searchExistingBookEdition(String isbn, String title, String author) {
        BookEdition bookEdition = new BookEdition("Tjenare", "Benare", "9781509302000");
        when(bookDao.find(isbn, title, author)).thenReturn(Set.of(bookEdition));

        Set<BookEdition> maybeBooks = controller.search(isbn, title, author, null);

        assertThat(maybeBooks).containsExactly(bookEdition);
        verify(bookDao, never()).withClass(any());
    }

    @Test
    void searchWithClassification() {
        DdsClassification dds = new DdsClassification(898, null);
        BookEdition bookEdition = new BookEdition("Hej", "Jag", "9781539302000");
        when(bookDao.withClass(dds)).thenReturn(Set.of(bookEdition));

        Set<BookEdition> maybeBooks = controller.search(bookEdition.getIsbn(), bookEdition.getTitle(),
                bookEdition.getAuthor(), "898");

        assertThat(maybeBooks).containsExactly(bookEdition);
        verify(bookDao, never()).find(any(), any(), any());
    }

    @Test
    void searchWithNoFilters() {
        when(bookDao.find(null, null, null)).thenReturn(Set.of());
        Set<BookEdition> maybeBook = controller.search(null, null, null, null);

        assertThat(maybeBook).isEmpty();
    }

    @Test
    void searchNonExistingBookEdition() {
        when(bookDao.find(null, "Hje", null)).thenReturn(Set.of());
        Set<BookEdition> maybeBook = controller.search(null, "Hje", null, null);

        assertThat(maybeBook).isEmpty();
    }
}
