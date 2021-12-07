package com.demo.config.constant;

/**
 * @author Alex
 * @date 2021/9/18 14:04
 */
public class SecurityConst {

    /**
     * token 过期时间
     *      24 h
     */
    public final static long TOKEN_EXPIRED = 24 * 60 * 60 * 1000;

    /**
     * Token 加解密 Key
     */
    public final static String TOKEN_SECRET_KEY = "4028c9817bf6e6eb017bf6e70fe10000";

    /**
     * 报文加密盐值
     */
    public final static  String SALT_VALUE = "newlandpayment";

}
