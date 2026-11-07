package se.yrgo.libraryapp.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.*;

import se.yrgo.libraryapp.entities.forms.*;
import se.yrgo.libraryapp.services.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class RegisterUserControllerTest {
    @Mock
    private UserService userService;

    private RegisterUserController controller;

    @BeforeEach
    void setUp() {
        controller = new RegisterUserController(userService);
    }

    @Test
    void registerWithValidInformation() {
        String username = "davidcool";
        String realname = "David Coolberg";
        String password = "password";
        RegisterUserData userData = new RegisterUserData(username, realname, password);
        when(userService.register(username, realname, password)).thenReturn(true);

        assertThat(controller.register(userData)).isTrue();
    }

    @Test
    void isNameAvailableWhenAvailable() {
        String name = "cooler";
        when(userService.isNameAvailable(name)).thenReturn(true);

        assertThat(controller.isNameAvailable(name)).isTrue();
    }

    @Test
    void isNameAvailableWhenNotAvailable() {
        String name = "cooler";
        when(userService.isNameAvailable(name)).thenReturn(false);

        assertThat(controller.isNameAvailable(name)).isFalse();
    }
}
