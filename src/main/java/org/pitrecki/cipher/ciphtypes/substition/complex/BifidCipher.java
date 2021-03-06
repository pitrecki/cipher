package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.pitrecki.cipher.interfaces.annotations.Decryption;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bifid is a cipher which combines the Polybius square with transposition, and uses
 * fractionation to achieve diffusion. It was invented by Felix Delastelle. Delastelle
 * was a Frenchman who invented several ciphers including the bifid, trifid, and
 * four-square ciphers. The first presentation of the bifid appeared in the
 * French Revue du Génie civil in 1895 under the name of cryptographie nouvelle.
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * @see PolybiusSquareCipher
 * Created by Pitrecki on 2017-01-04.
 */
public class BifidCipher extends PolybiusSquareCipher
{
    public BifidCipher() {
        super();
    }

    public BifidCipher(String key) throws InvalidKeyException {
        super(key);
    }

    @Override
    public void encrypt(String inputText) {
        inputText = checkIfInputTextContainsOnlyLetter(inputText);

        List<Integer> coordinates = new ArrayList<>();
        fillListWithCoordinates(inputText, coordinates);

        coordinates = swapOrderOfCoordinates(coordinates);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < coordinates.size(); i+=2) {
            int rowVal = coordinates.get(i);
            int columnVal = coordinates.get(i + 1);
            builder.append(getValueFromEncryptMatrix(rowVal, columnVal));
        }

        setProcessedText(builder.toString());
    }

    @Decryption
    private List<Integer> swapOrderOfCoordinates(List<Integer> coordinates) {
        Integer[] integers = new Integer[coordinates.size()];
        int arrayIndex = 0;
        int halfSize = coordinates.size() / 2;
        for (int i = 0; i < coordinates.size(); i+=2) {
            integers[arrayIndex] = coordinates.get(i);
            integers[arrayIndex + halfSize] = coordinates.get(i + 1);
            arrayIndex++;
        }
        return Arrays.asList(integers);
    }


    @Override
    public void decrypt(String inputText) {
        inputText = checkIfInputTextContainsOnlyLetter(inputText);

        List<Integer> coordinates = new ArrayList<>();

        fillListWithCoordinates(inputText, coordinates);


        StringBuilder builder = new StringBuilder();
        int halfSize = coordinates.size() / 2;
        for (int i = 0; i < inputText.length();i++) {
            int rowVal = coordinates.get(i);
            int columnVal = coordinates.get(halfSize + i);
            builder.append(getValueFromEncryptMatrix(rowVal, columnVal));
        }

        setProcessedText(builder.toString());

    }


    private String checkIfInputTextContainsOnlyLetter(String text) {
        if (text.matches(".*[\\p{Digit}\\p{Punct}].*"))
            throw new IllegalArgumentException("Only letters, your input" + text);

        return textProcessing(text);
    }
}
