package com.demo.utils.encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;

/**
 * @author Alex
 * @date 2021/7/1 15:34
 */
public class RSAUtils {

    private static Cipher cipher = null;

    private static KeyPairGenerator keyPairGenerator = null;

    private static final String ALGORITHM = "RSA";

    private static final int DEFAULT_KEY_SIZE = 1024;

    static {
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static KeyPair getKeyPair() {
        return getKeyPair(DEFAULT_KEY_SIZE);
    }

    public static KeyPair getKeyPair(int keySize) {

        keyPairGenerator.initialize(keySize);

        return keyPairGenerator.generateKeyPair();

    }

    public static String encrypt(PublicKey publicKey, String plainText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    public static String decrypt(PrivateKey privateKey, String encryptedText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }

    public static void main(String[] args) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String plainText = "Hello Hello Hello";

        KeyPair keyPair = getKeyPair();

        PublicKey publicKey = keyPair.getPublic();

        PrivateKey privateKey = keyPair.getPrivate();

        //公钥加密获取密文
        String encrypted = encrypt(publicKey, plainText);

        //私钥解密获取明文
        String decrypted = decrypt(privateKey, encrypted);

        System.out.println(encrypted);

        System.out.println(decrypted);
    }

}
