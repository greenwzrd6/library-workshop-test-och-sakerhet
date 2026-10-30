package se.yrgo.libraryapp.validators;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.assertj.core.api.Assertions.*;

public class UsernameTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "boss",
            "BOSS",
            "____",
            "----",
            "@@@@",
            "....",
            "@-_.",
            "Bayern-Mannen",
            "123123123",
            "BosseThaKillR1337",
            "0000"
    })
    void validateWhenValidUsernameReturnsTrue(String validUsername) {
        assertThat(Username.validate(validUsername)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " ",
            "😍",
            "😍😍😍😍😍😍😍😍",
            ",,,,",
            "Bayern-München",
            "      ",
            "tjenare mannen",
            "                                                            Bosse",
            "åäöåä",
            "!!!!!!!!!!!!?????",
            "123"
    })
    void validateWhenInvalidUsernameReturnsFalse(String invalidUsername) {
        assertThat(Username.validate(invalidUsername)).isFalse();
    }

    @ParameterizedTest
    @EmptySource
    void validateWhenEmptyUsernamesReturnsFalse(String emptyUsernames) {
        assertThat(Username.validate(emptyUsernames)).isFalse();
    }

    @ParameterizedTest
    @NullSource
    void validateWhenUsernameIsNullThrows(String nullUsername) {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Username.validate(nullUsername));
    }

}
