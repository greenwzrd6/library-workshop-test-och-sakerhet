package se.yrgo.libraryapp.validators;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.assertj.core.api.Assertions.*;

public class UtilsTest {

    @ParameterizedTest
    @CsvSource({
            "Apan mannen 13 2 Hej,        apan mannen   hej",
            "123 korv.se jag heter bert,  ' korvse jag heter bert'",
            "@@@@@@,                      ''",
            "TeJEnoRska,                  tejenorska",
            "åöäÅÅÅ,                      åöäååå",
            "'   ',                       '   '",
            "'🙈 hej, hihi',              ' hej hihi'",
            "!!!!!!!!,                    ''",
            "😎😎😎😎😎😎😎😎😎😎😎😎😎😎,     ''",
            "'üüÄ¨¨¨¨ ',                  'üüä '",
            "жжллл,                       жжллл"
    })
    void onlyLettersAndWhitespaceWhenValidStringReturnsCorrectString(
            String input,
            String expectedOutput) {
        assertThat(Utils.onlyLettersAndWhitespace(input)).isEqualTo(expectedOutput);
    }

    @ParameterizedTest
    @NullSource
    void onlyLettersAndWhitespaceWhenNullThrows(String nullString) {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Utils.onlyLettersAndWhitespace(nullString));
    }

    @ParameterizedTest
    @CsvSource({
            "Ki11Z0r,                killzor",
            "d4v37h4Hun73R,          davethahunter",
            "m3G4H4 X X 0R,          megaha x x or",
            "I 4M 1337 H4xx0R,       i am leet haxxor",
            "'',                     ''",
            "' ',                    ' '",
            "H3js4n j4g h373r D4VID, hejsan jag heter david",
            "29,                     ''",
            "6068y,                  bobby",
            "😎😎😎 😎😎😎😎,          ' '",
            "'Üüä¨¨¨¨ ',             'üüä '",
            "killzor,                killzor",
            "DaveTheHunter,          davethehunter",
            "ЖЖЛЛЛ,                  жжллл"

    })
    void cleanAndUnLeetWhenValidStringReturnsCorrectString(
            String input,
            String expectedOutput) {
        assertThat(Utils.cleanAndUnLeet(input)).isEqualTo(expectedOutput);
    }

    @ParameterizedTest
    @NullSource
    void cleanAndUnLeetWhenNullThrows(String nullString) {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Utils.cleanAndUnLeet(nullString));
    }
}
