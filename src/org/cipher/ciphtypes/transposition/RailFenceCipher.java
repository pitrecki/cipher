package org.cipher.ciphtypes.transposition;

import java.util.Arrays;

/**
 * The <b>rail fence cipher </b> (also called a zigzag cipher) is a form of transposition cipher.
 * It derives its name from the way in which it is encoded.
 *
 * The railfence cipher is a very simple, easy to crack cipher. It is a transposition cipher that
 * follows a simple rule for mixing up the characters in the plaintext to form the ciphertext. The
 * railfence cipher offers essentially no communication security, and it will be shown that it can
 * be easily broken even by hand. Although weak on its own, it can be combined with other ciphers,
 * such as a substitution cipher, the combination of which is more difficult to break than either
 * cipher on it's own. Many websites claim that the rail-fence cipher is a simpler "write down the
 * columns, read along the rows" cipher. This is equivalent to using an un-keyed columnar
 * transposition cipher
 *
 * FOR MORE INFORMATION LOOK AT: <a href="">LINK</a>
 *
 * ALSO CHECK THIS
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.1
 * Created by Pitrecki on 2016-10-28.
 *
 */
public class RailFenceCipher extends TranspositionCipher
{
    private final int RAIL_KEY;

    public RailFenceCipher(int key) {
        super();
        this.RAIL_KEY = key;
    }

    /**
     *
     * @param textToDecode
     */

    @Override
    protected void cryptArrayGenerator(String textToDecode) {
        String temp = textToDecode.replaceAll(" ", "");
        setCryptMatrix(new Character[RAIL_KEY][temp.length()]);

//        char[] textToCharArray = temp.toCharArray();
        int textCharIndex = 0;
        try {
            for (int j = 0; j < getCryptMatrix()[0].length; j += RAIL_KEY + 1) {
                int row = 0;
                int column = j;
                do {
                    char letter = temp.charAt(textCharIndex);
                    setValueInCryptMatrix(row, column, letter);
                    row++;
                    column++;
                    textCharIndex++;
                } while (row < getCryptMatrix().length);

                row -= 1;
                column -= 1;

                do {
                    row--;
                    column++;
                    char letter = temp.charAt(textCharIndex);
                    setValueInCryptMatrix(row, column, letter);
                    textCharIndex++;
                } while (row > 1);
            }
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {}

    }

    @Override
    public void encrypt(String inputText) {
        cryptArrayGenerator(inputText);

        Character[][] tmpCryptMatrix = ((Character[][]) getCryptMatrix());
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < tmpCryptMatrix.length; i++)
            buffer.append(Arrays.toString(tmpCryptMatrix[i]));

        setProcessedText(buffer.toString());
    }

    @Override
    public void decrypt(String inputText) {
        cryptArrayGenerator(inputText);
        //TODO Poprawic implentacje, nie dziala jak nalezy
        //FIXME Napraw to wreszcie!
        Character[][] tmpCryptMatrix = ((Character[][]) getCryptMatrix());
        StringBuffer buffer = new StringBuffer();

//       += (2*RAIL_KEY) - 2
//        for (int i = 0; i < tmpCryptMatrix.length; i++) {
//            for (int j = i; j < tmpCryptMatrix[i].length; j ++) {
////                if (buffer.length() < RAIL_KEY)
//                buffer.append(tmpCryptMatrix[i][j]);
//
//            }
//        }

        for (int i = 0; i < tmpCryptMatrix[0].length; i++) {
            for (int j = 0; j < tmpCryptMatrix.length; j++) {
                buffer.append(tmpCryptMatrix[j][i]);
            }
        }




        setProcessedText(buffer.toString());
    }

}
