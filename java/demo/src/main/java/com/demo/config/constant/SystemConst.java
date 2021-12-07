package com.demo.config.constant;

/**
 * @author Alex
 * @date 2021/9/27 9:31
 */
public class SystemConst {

    /**
     * currentUser session
     */
    public static final String CURRENT_USER = "currentUser";

    /**
     * currentOrganId session
     */
    public static final String CURRENT_ORGAN_ID = "currentOrganId";

    /**
     * currentPage session
     */
    public static final String CURRENT_PAGE = "currentPage";

    /**
     * pageSize session
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "888888";

    /**
     * 账号密码错误多少次锁定
     */
    public static final long MAX_PWD_ERROR_TIME = 5;

    /**
     * 账号锁定时长
     */
    public static final long ACCOUNT_LOCK_TIME = 60 * 60 * 1000;

    /**
     * 状态常量
     */
    public static class Status{
        public static final String WAIT = "WAIT";
        public static final String START = "START";
        public static final String EXCEPTION = "EXCEPTION";
        public static final String END = "END";
        public static final String STOP = "STOP";
        public static final String ABORT = "ABORT";
    }

}
