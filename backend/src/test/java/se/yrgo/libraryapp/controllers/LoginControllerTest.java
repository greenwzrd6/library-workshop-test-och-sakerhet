package se.yrgo.libraryapp.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.Strictness;

import io.jooby.*;
import se.yrgo.libraryapp.dao.*;
import se.yrgo.libraryapp.entities.*;
import se.yrgo.libraryapp.entities.forms.*;
import se.yrgo.libraryapp.services.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class LoginControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private RoleDao roleDao;
    @Mock
    private SessionDao sessionDao;
    @Mock
    private Context context;

    private LoginController controller;

    @BeforeEach
    void setUp() {
        controller = new LoginController(userService, roleDao, sessionDao);
    }

    @Test
    void loginWithCorrectDataReturnsCorrectRole() {
        UserId userId = UserId.of(6);
        UUID sessionId = UUID.randomUUID();
        LoginData login = new LoginData("david", "brapassword123!");

        when(userService.validate(login.getUsername(), login.getPassword()))
                .thenReturn(Optional.of(userId));
        when(sessionDao.create(userId)).thenReturn(sessionId);
        when(roleDao.get(userId)).thenReturn(List.of(Role.USER));

        List<Role> roles = controller.login(context, null, login);

        assertThat(roles).containsExactly(Role.USER);
    }
}
