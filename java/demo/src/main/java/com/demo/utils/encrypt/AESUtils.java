package com.demo.utils.encrypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author Alex
 * @date 2021/7/1 16:10
 */
public class AESUtils {

    private static Cipher cipher = null;

    private static KeyGenerator keyGenerator = null;

    private static final int DEFAULT_KEY_SIZE = 128;

    static {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            keyGenerator = KeyGenerator.getInstance("AES");
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

    public static String decrypt(Key key, String decryptedText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        cipher.init(Cipher.DECRYPT_MODE, key);

        return new String(cipher.doFinal(Base64.getDecoder().decode(decryptedText)));

    }

    public static void main(String[] args) throws Exception{
        //指定使用AES加密
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //使用KeyGenerator生成key，参数与获取cipher对象的algorithm必须相同
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //指定生成的密钥长度为128
        keyGenerator.init(128);
        Key key = keyGenerator.generateKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal("helloworld".getBytes());
        System.out.println("AES加密： " + Base64.getEncoder().encodeToString(bytes));

        //由于AES加密在CBC模式下是需要有一个初始向量数组byte[] initializeVector ,
        // 而解密的时候也需要同样的初始向量，因此需要使用加密时的参数初始化解密的cipher，否则会出错
        byte[] initializeVector = cipher.getIV();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializeVector);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        //上面三步操作可以用此操作代替   cipher.init(Cipher.DECRYPT_MODE, configName, cipher.getParameters());
        bytes = cipher.doFinal(bytes);
        System.out.println("AES解密： " + new String(bytes));
    }

}
