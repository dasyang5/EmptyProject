package com.demo.utils.encrypt;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author Alex
 * @date 2021/7/1 15:58
 */
public class DESUtils {

    private static Cipher cipher = null;

    private static KeyGenerator keyGenerator = null;

    private static final String ALGORITHM = "DES";

    private static final int DEFAULT_KEY_SIZE = 56;

    static {
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static Key getKey() {
        return getKey(DEFAULT_KEY_SIZE);
    }

    public static Key getKey(int keySize) {

        keyGenerator.init(keySize);

        return keyGenerator.generateKey();

    }

    public static String encrypt(Key key, String plainText) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    public static String decrypt(Key key, String encryptedText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, key);

        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        String plain = "Hello Hello Hello";

        Key key = getKey();

        String encrypted = encrypt(key, plain);

        String decrypted = decrypt(key, encrypted);

        System.out.println(plain);
        System.out.println(encrypted);
        System.out.println(decrypted);
    }

}
