package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.ciphtypes.substition.complex.baconian.BaconianCipher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.pitrecki.cipher.ciphtypes.substition.complex.baconian.BaconianCipher.Variant.DISTINCT;
import static org.pitrecki.cipher.ciphtypes.substition.complex.baconian.BaconianCipher.Variant.STANDARD;
import static org.pitrecki.cipher.utils.TestContainer.*;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2016-12-28.
 */
class BaconianCipherTest
{
    private Cipher baconianCipher;

    private List<String> listInit(String... params) {
        return Arrays.stream(params)
                .map(s -> s.replaceAll("[\\W\\p{Digit}\\p{Punct}]", ""))
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("Testing of text encryption using standard type (24 letters)")
    void testStandardEncryption() {
        this.baconianCipher = new BaconianCipher(STANDARD);

        List<String> plaintextList = listInit(ALPHABET, SHORT_MESSAGE, LONG_MESSAGE);

        String expectedAlphabet = "AAAAAAAAABAAABAAAABBAABAAAABABAABBAAABBBABAAAABAAAABAABABABAABABBABB" +
                                  "AAABBABABBBAABBBBBAAAABAAABBAABABAABBBAABBBABAABABABBABBABABBB";

        String expectedShortMessage = "BAAABBAABABAAAAABAAAABAABAABAAABBAAABBABBABAA";

        String expectedLongMessage =  "BAABAAABBBAABAAAABBAABBABABABAAAABBABAAABAAABAAAABBAABBBAAAAABAAA" +
                                      "AABAAAAABBABAAAABBAAABBABBAAAAABBABABBAAABBAB";

        List<String> expectedList = listInit(expectedAlphabet, expectedShortMessage, expectedLongMessage);

        List<String> actualList = Stream.of(plaintextList)
                .flatMap(strings -> strings.stream()
                        .map(this::invokeEncryptionAndReturnEncryptedText))
                .collect(Collectors.toList());

        assertThat(actualList).isEqualTo(expectedList);
    }

    private String invokeEncryptionAndReturnEncryptedText(String s) {
        baconianCipher.encrypt(s);
        return baconianCipher.getProcessedText();
    }

    @Test
    @DisplayName("Testing of correct encryption using distinct mode (26 letters, full alphabet).")
    void testDistinctEncryption() {
        this.baconianCipher = new BaconianCipher(DISTINCT);

        List<String> plaintextList = listInit(ALPHABET, SHORT_MESSAGE, LONG_MESSAGE);

        String expectedAlphabet = "AAAAAAAAABAAABAAAABBAABAAAABABAABBAAABBBABAAAABAABABABAABABBABBAA" +
                                  "ABBABABBBAABBBBBAAAABAAABBAABABAABBBABAABABABBABBABABBBBBAAABBAAB";

        String expectedShortMessage = "BAABABAABBBAAABABAAAABABAAABAAABBABABBBABABBA";

        String expectedLongMessage = "BAABBAABBBAABAAAABBAABBBAABABBAAABBABAAABAABAAAAABBABAABAAABABA" +
                                     "AAAABAAAAABBABAAAABBABABBBABAAABABBBAABBABABBBA";

        List<String> expectedList = listInit(expectedAlphabet, expectedShortMessage, expectedLongMessage);

        List<String> actualList = Stream.of(plaintextList)
                .flatMap(strings -> strings.stream()
                        .map(this::invokeEncryptionAndReturnEncryptedText))
                .collect(Collectors.toList());

        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    @DisplayName("Testing of correct decryption using standard mode")
    void testStandardDecryption() {
        this.baconianCipher = new BaconianCipher(STANDARD);

        String expected = LONG_MESSAGE;
        String plaintext =  "BAABAAABBBAABAAAABBAABBABABABAAAABBABAAABAAABAAAABBAABBBAAAAABAAA" +
                            "AABAAAAABBABAAAABBAAABBABBAAAAABBABABBAAABBAB";

        baconianCipher.decrypt(plaintext);
        String actual = baconianCipher.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing of correct decryption using distinct mode")
    void testDistinctDecryption() {
        this.baconianCipher = new BaconianCipher(DISTINCT);

        String expected = LONG_MESSAGE;
        String plaintext = "BAABBAABBBAABAAAABBAABBBAABABBAAABBABAAABAABAAAAABBABAABAAABABA" +
                           "AAAABAAAAABBABAAAABBABABBBABAAABABBBAABBABABBBA";

        baconianCipher.decrypt(plaintext);
        String actual = baconianCipher.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }
}