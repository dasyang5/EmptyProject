package com.demo.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Alex
 * @date 2021/9/23 14:39
 */
@Component
public class ApplicationYML {

    @Value("${atls.file.base-path}")
    public String ATLS_FILE_BASE_PATH;


    @Value("${atls.file.req-path}")
    public String reqPath;
}
