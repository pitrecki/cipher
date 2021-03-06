package org.pitrecki.cipher.ciphtypes.substition.simple;

import org.pitrecki.cipher.utils.CryptVariant;

/**
 * In cryptography, a <b>Caesar cipher</b>, also known as Caesar's cipher, the shift cipher,
 * Caesar's code or Caesar shift, is one of the simplest and most widely known encryption techniques.
 * It is a type of substitution cipher in which each letter in the plaintext is replaced by a
 * letter some fixed number of positions down the alphabet. For example, with a left shift of 3,
 * D would be replaced by A, E would become B, and so on. The method is named after Julius Caesar,
 * who used it in his private correspondence.
 * The encryption step performed by a Caesar cipher is often incorporated as part of more complex schemes,
 * such as the Vigenère cipher, and still has modern application in the ROT13 system. As with all
 * single-alphabet substitution ciphers, the Caesar cipher is easily broken and in modern practice offers
 * essentially no communication security.
 * <p>
 * <b>For more information look at:</b> <a href="https://en.wikipedia.org/wiki/Caesar_cipher"> LINK </a>
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * @see SimpleSubstitutionCipher
 */
public class CaesarCipher extends SimpleSubstitutionCipher
{

    /**
     * CALL SimpleSubstitutionCipher and setting MASTER KEY to value 3!
     * KEY set on 3 by default
     */
    public CaesarCipher() {
        super(3);
    }


/*
    This group of method provide default rEncrypt and rDecrypt functional specified to THIS type of
    cipher algorithm

    SO PLEAEE DON'T MISSMATCH WITH METHODS OF CIPHER INTERFACE

    getAsciiTable. length returns array length specified in master class constructor
 */

    @Override
    int shift(int value, CryptVariant cryptVariant) {
        if (cryptVariant.equals(CryptVariant.ENCRYPT))
            return (value + getMasterKEY()) % ASCII_TABLE.length;
        else if (cryptVariant.equals(CryptVariant.DECRYPT))
            return (value - getMasterKEY()) % ASCII_TABLE.length;

        return -1;
    }
}
