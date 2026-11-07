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
public class UserControllerAdminTest {
    @Mock
    private UserDao userDao;

    private UserController controller;

    @BeforeEach
    void setUp() {
        controller = new UserController(userDao);
    }

    @Test
    void getUserWithExistingUser() {
        UserId userId = UserId.of(1);
        User user = new User(userId, "Hej", "Brej hej");
        when(userDao.get(userId.toString())).thenReturn(Optional.of(user));

        User maybeUser = controller.getUser(userId.toString());

        assertThat(maybeUser).isEqualTo(user);
    }

    @Test
    void getUserWithNonExistingUser() {
        UserId userId = UserId.of(1);
        when(userDao.get(userId.toString())).thenReturn(Optional.empty());

        User maybeUser = controller.getUser(userId.toString());

        assertThat(maybeUser).isNull();
    }
}
