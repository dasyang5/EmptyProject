package com.demo.utils.encrypt;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author Alex
 * @date 2021/7/1 16:10
 */
public class DES3Utils {

    private static Cipher cipher = null;

    private static KeyGenerator keyGenerator = null;

    private static final String ALGORITHM = "DES";

    private static final int DEFAULT_KEY_SIZE = 56;

    private static final int ENCRYPT_COUNT = 3;

    static {
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static Key[] getKeys() {
        return getKeys(DEFAULT_KEY_SIZE);
    }

    public static Key[] getKeys(int keySize) {

        keyGenerator.init(keySize);

        Key[] keys = new Key[ENCRYPT_COUNT];

        for (int i = 0; i < ENCRYPT_COUNT; i++) {
            keys[i] = keyGenerator.generateKey();
        }

        return keys;
    }

    public static String encrypt(Key[] keys, String plainText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        byte[] bytes = plainText.getBytes();

        for (int i = 0; i < ENCRYPT_COUNT; i++) {
            cipher.init(Cipher.ENCRYPT_MODE, keys[i]);
            bytes = cipher.doFinal(bytes);
        }

        return Base64.getEncoder().encodeToString(bytes);

    }


    public static String decrypt(Key[] keys, String decryptedText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        byte[] bytes = Base64.getDecoder().decode(decryptedText);

        for (int i = ENCRYPT_COUNT - 1; i >= 0; i--) {
            cipher.init(Cipher.DECRYPT_MODE, keys[i]);
            bytes = cipher.doFinal(bytes);
        }

        return new String(bytes);

    }

    public static void main(String[] args) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        String plain = "Hello Hello Hello";

        Key[] keys = getKeys();

        String encrypted = encrypt(keys, plain);

        String decrypted = decrypt(keys, encrypted);

        System.out.println(plain);
        System.out.println(encrypted);
        System.out.println(decrypted);
    }

}
