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
            "Jonas Rumpnisse",
            "Idiota Peerson",
            "Snåljåp ßeln",
            "Жhej Bombad",
            "Bombad Man"
    })
    void validateWhenInvalidNameReturnsFalse(String invalidName) {
        assertThat(RealName.validate(invalidName)).isFalse();
    }
}
