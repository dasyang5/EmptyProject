package com.demo.config.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author Alex
 * @date 2021/10/9 13:59
 */
@Component
public class MyI18n {

    private MessageSource messageSource;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public MyI18n(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 获取单个国际化翻译值
     */
    public String get(String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, getLocale());
        } catch (Exception e) {
            return msgKey;
        }
    }

    /**
     * 获取单个国际化翻译值
     */
    public String get(String msgKey, String local) {
        try {
            if (local.equals("en_US")){
                return messageSource.getMessage(msgKey, null, Locale.SIMPLIFIED_CHINESE);
            }else {
                return messageSource.getMessage(msgKey, null, Locale.US);
            }
        } catch (Exception e) {
            return msgKey;
        }
    }

    public String get(String msgKey, Object[] args) {
        return messageSource.getMessage(msgKey, args, getLocale());
    }

    /**
     * 从请求头取出语种
     * @return
     */
    public Locale getLocale() {
        Locale locale;

        String lang = httpServletRequest.getHeader("Accept-Language");

        if ("zh".equalsIgnoreCase(lang)) {
            locale = Locale.SIMPLIFIED_CHINESE;
        } else {
            locale = Locale.US;
        }

        return locale;
    }

}
