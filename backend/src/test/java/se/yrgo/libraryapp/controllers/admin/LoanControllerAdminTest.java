package se.yrgo.libraryapp.controllers.admin;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.*;

import se.yrgo.libraryapp.dao.*;
import se.yrgo.libraryapp.entities.*;
import se.yrgo.libraryapp.entities.forms.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class LoanControllerAdminTest {
    @Mock
    private BookDao bookDao;

    private LoanController controller;

    @BeforeEach
    void setUp() {
        controller = new LoanController(bookDao);
    }

    @Test
    void lendAvailableBook() {
        BookId bookId = BookId.of(1);
        UserId userId = UserId.of(1);
        LoanData loan = new LoanData(bookId, userId);

        when(bookDao.lend(bookId, userId)).thenReturn(true);

        assertThat(controller.lendBook(loan)).isTrue();
    }

    @Test
    void lendUnavailableBook() {
        BookId bookId = BookId.of(1);
        UserId userId = UserId.of(1);
        LoanData loan = new LoanData(bookId, userId);

        when(bookDao.lend(bookId, userId)).thenReturn(false);

        assertThat(controller.lendBook(loan)).isFalse();
    }

    @Test 
    void returnLoanedBook() {
        BookId bookId = BookId.of(1);

        when(bookDao.returnBook(bookId)).thenReturn(true);

        assertThat(controller.returnBook(bookId)).isTrue();
    }

    @Test 
    void returnNonLoanedBook() {
        BookId bookId = BookId.of(1);

        when(bookDao.returnBook(bookId)).thenReturn(false);

        assertThat(controller.returnBook(bookId)).isFalse();
    }

    // Add tests for overdue
}
