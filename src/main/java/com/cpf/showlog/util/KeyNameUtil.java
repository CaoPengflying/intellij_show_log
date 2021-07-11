package com.cpf.showlog.util;

/**
 * 字符串常量
 *
 * @author lk
 * @version 1.0
 * @date 2020/8/23 17:14
 */
public class KeyNameUtil {
    public static final String PROJECT_ID = "ShowLogPlugin";
    public static final String FILTER_KEY = "[SQL]";
    public static final String FILTER_KEY_SUFFIX = "took";
    public static final String LINE = " ";
    /**
     * 无法格式输出
     */
    public static final String SQL_NULL = "没有可以格式化内容";
    public static final String SQL_ERROR = "sql异常";
    public static final String SQL_Line = "--  ";
    /**
     * 存储的key名称
     */
    public static final String DB_PREPARING_KEY = PROJECT_ID + "[SQL]";
    /**
     * 存储的key名称
     */
    public static final String DB_PREPARING_SUFFIX_KEY = PROJECT_ID + "took";
    /**
     * 存储的key名称 是否允许运行
     */
    public static final String DB_STARTUP_KEY = PROJECT_ID + "startup";
    /**
     * 参数过滤正则表达式
     */
    public static final String PARAM_REGEXP = "\\[]interface \\{}\\{[\\\"\\w \\s^%&',;=?$\\x22#-:\u4e00-\u9fa5]*}";
    public static final String PARAM_PRE_REGEXP = "\\[]interface \\{}";
    public static final String PARAM_LEFT_REGEXP = "\\{";
    public static final String PARAM_RIGHT_REGEXP = "}";
    /**
     * sql 过滤正则表达式
     */
    public static final String SQL_STATEMENT_REGEXP = "\\[SQL][\\w\\s^%&',;*=?$\\x22\\\"#`\\()<>]*";
    public static final String SQL_PRE_REGEXP = "\\[SQL]";

    public static final String SQL_REGEXP = "\\[SQL][\\w\\s^%&',;8*=?$\\x22\\\"#`\\()<>]*\\[]interface \\{}\\{[\\\"\\w \\s^%&',;=?$\\x22#-:一-龥]*} - took: [\\d.]*ms";
}
