package com.demo.utils.file;

import java.io.File;

/**
 * @author Alex
 * @date 2021/10/27 14:11
 */
public class ApplicationFileUtils {

    public static String getFileName(String basePath, String organId, String appCode, String appVer, String fileName) {
        return basePath + File.separator + organId + File.separator + appCode + File.separator + appVer + File.separator + fileName;
    }

}
