package se.yrgo.libraryapp.controllers;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.*;

import io.jooby.*;
import se.yrgo.libraryapp.dao.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class LogoutControllerTest {
    @Mock
    private SessionDao sessionDao;
    @Mock
    private Context context;

    private LogoutController controller;

    @BeforeAll
    void setUp() {
        controller = new LogoutController(sessionDao);
    }

    //Ingen aning om hur man skriver tester här
}
