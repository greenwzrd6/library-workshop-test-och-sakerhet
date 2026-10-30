package se.yrgo.libraryapp.validators;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

public class RealNameTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "Jonas Bonas",
            "Päron Peerson",
            "Günter ßeln",
            "Жhej Hejsan"
    })
    void validateWhenValidNameReturnsTrue(String validName) {
        assertThat(RealName.validate(validName)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Rumpnisse Jonas",
            "Idiota Peerson",
            "dinmamma ßeln",
            "Bombad Man",
            "snåljåp Man",
            "Dinpappa mamma",
            "APNISSE snapnisse"
    })
    void validateWhenInvalidFirstNameReturnsFalse(String invalidName) {
        assertThat(RealName.validate(invalidName)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Jonas Rumpnisse",
            "Peerson Idiota",
            "ßeln dinmamma",
            "Man Bombad",
            "Man snÅljåp",
            "mamma Dinpappa",
            "snapnisse APNISS3"
    })
    void validateWhenInvalidLastNameReturnsFalse(String invalidName) {
        assertThat(RealName.validate(invalidName)).isFalse();
    }

    @ParameterizedTest
    @NullSource
    void validateWhenNullNameReturnsFalse(String nullName) {
        assertThat(RealName.validate(nullName)).isFalse();
    }
}
