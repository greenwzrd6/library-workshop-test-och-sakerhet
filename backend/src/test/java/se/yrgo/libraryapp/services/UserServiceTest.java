package se.yrgo.libraryapp.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.*;
import org.springframework.security.crypto.password.*;

import se.yrgo.libraryapp.dao.*;
import se.yrgo.libraryapp.entities.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @Test
    @SuppressWarnings("deprecation")
    void correctLogin() {
        final String userId = "1";
        final UserId id = UserId.of(userId);
        final String username = "testuser";
        final String password = "password";
        final String passwordHash = "password";
        final LoginInfo info = new LoginInfo(id, passwordHash);
        final PasswordEncoder encoder = org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();

        when(userDao.getLoginInfo(username)).thenReturn(Optional.of(info));

        UserService userService = new UserService(userDao, encoder);
        assertThat(userService.validate(username, password)).isEqualTo(Optional.of(id));
    }

    @Test
    @SuppressWarnings("deprecation")
    void correctRegister() {
        final String userId = "23";
        final UserId id = UserId.of(userId);
        final String username = "benare";
        final String fullname = "Tjenare B'enare";
        final String password = "password";
        final String passwordHash = "password";
        final LoginInfo info = new LoginInfo(id, passwordHash);
        final PasswordEncoder encoder = org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();

        when(userDao.isNameAvailable(username)).thenReturn(true);
        when(userDao.register(username, fullname, passwordHash)).thenReturn(true);
        when(userDao.getLoginInfo(username)).thenReturn(Optional.of(info));

        UserService userService = new UserService(userDao, encoder);
        assertThat(userService.register(username, fullname, password)).isTrue();
        assertThat(userService.validate(username, password)).isEqualTo(Optional.of(id));
    }

    @Test
    @SuppressWarnings("deprecation")
    void incorrectNameRegister() {
        final String username = "benare";
        final String fullname = "Tjenare B'enare";
        final String password = "password";
        final PasswordEncoder encoder = org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();

        when(userDao.isNameAvailable(username)).thenReturn(false);

        UserService userService = new UserService(userDao, encoder);
        assertThat(userService.register(username, fullname, password)).isFalse();
        verify(userDao, never()).register(any(), any(), any());
    }

    @Test
    @SuppressWarnings("deprecation")
    void nullPasswordRegister() {
        final String username = "benare";
        final String fullname = "Tjenare B'enare";
        final String password = null;
        final PasswordEncoder encoder = org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();

        UserService userService = new UserService(userDao, encoder);
        assertThat(userService.register(username, fullname, password)).isFalse();
        verify(userDao, never()).register(any(), any(), any());
    }
}
