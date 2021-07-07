package com.cpf.showlog.util;

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于SQL的还原
 *
 * @author lk
 * @version 1.0
 * @date 2020/8/23 17:14
 */
public class SqlProUtil {

    private final static BasicFormatter BASIC_FORMATTER = new BasicFormatter();

    /**
     * 匹配 '
     */
    private final static Pattern PSingleQuotationMark = Pattern.compile("'");
    /**
     * 匹配 (String),
     */
    public static final Pattern paramPattern = Pattern.compile(KeyNameUtil.PARAM_REGEXP);
    public static final Pattern paramPrePattern = Pattern.compile(KeyNameUtil.PARAM_PRE_REGEXP);
    public static final Pattern paramLeftPattern = Pattern.compile(KeyNameUtil.PARAM_LEFT_REGEXP);
    public static final Pattern paramRightPattern = Pattern.compile(KeyNameUtil.PARAM_RIGHT_REGEXP);
    public static final Pattern sqlStatementPattern = Pattern.compile(KeyNameUtil.SQL_STATEMENT_REGEXP);
    public static final Pattern sqlPrePattern = Pattern.compile(KeyNameUtil.SQL_PRE_REGEXP);
    /**
     * sql 过滤正则表达式
     */
    public static final String SQL_STATEMENT_REGEXP = "\\[SQL][\\w\\s^%&',;=?$\\x22\\\"#`\\()<>]*";


    public static Boolean Ellipsis = false;

    /**
     * 获取Sql语句类型
     *
     * @param sql 语句
     * @return String
     */
    public static String getSqlType(String sql) {
        if (StringUtils.isNotBlank(sql)) {
            String lowerLine = sql.toLowerCase().trim();
            if (lowerLine.startsWith("insert")) {
                return "insert";
            }
            if (lowerLine.startsWith("update")) {
                return "update";
            }
            if (lowerLine.startsWith("delete")) {
                return "delete";
            }
            if (lowerLine.startsWith("select")) {
                return "select";
            }
        }
        return "";
    }

    /**
     * Sql语句还原，整个插件的核心就是该方法
     *
     * @param sql sql
     * @return
     */
    public static String restoreSql(final String sql) throws Exception {
        final String filterPre = ConfigUtil.getFilterPre();
        return restoreSql(filterPre, sql);
    }

    /**
     * Sql语句还原，整个插件的核心就是该方法
     *
     * @param filterPre sql
     * @param sql       参数
     * @return
     */
    public static String restoreSql(String filterPre, final String sql) throws Exception {
        String param = getSqlParam(sql);
        String sqlStatement = getSqlStatement(sql);

        return assemblyFormatSql(param, sqlStatement);
    }

    /**
     * 组装最后的sql
     *
     * @param param
     * @param sqlStatement
     * @return
     */
    private static String assemblyFormatSql( String param, String sqlStatement) throws Exception {
        StringBuilder resultSql = new StringBuilder();
        int idx = 0;
        String[] split = StringUtils.split(param, ",");

        for (int i = 0; i < sqlStatement.length(); i++) {
            if (sqlStatement.charAt(i) == '?') {
                if (idx >= split.length) {
                    throw new Exception("sql 异常");
                }
                resultSql.append(split[idx]);
                continue;
            }
            resultSql.append(sqlStatement.charAt(i));
        }
        return resultSql.toString();
    }

    private static String getSqlStatement(String sql) {
        Matcher sqlMatcher = sqlStatementPattern.matcher(sql);
        String sqlStatement = "";
        if (sqlMatcher.find()) {
            sqlStatement = sqlMatcher.group();
            sqlStatement = sqlPrePattern.matcher(sqlStatement).replaceAll("");
        }
        return sqlStatement;
    }

    private static String getSqlParam(String sql) {
        Matcher paramMatcher = paramPattern.matcher(sql);
        String param = "";
        if (paramMatcher.find()) {
            param = paramMatcher.group();
            param = paramPrePattern.matcher(param).replaceAll("");
            param = paramLeftPattern.matcher(param).replaceAll("");
            param = paramRightPattern.matcher(param).replaceAll("");
        }
        return param;
    }

    /**
     * 处理Like的%符号冲突String.format方法
     *
     * @return
     */
    private static String ProcessLikeSymbol(String preparing) {
        return preparing.replaceAll("∮∝‰#‰∝∮", "%");
    }

    /**
     * 处理Substring函数正则表达式，不兼容的问题
     *
     * @return
     */
    private static String ProcessSubstringSymbol(String preparing, HashMap<Object, Object> hashMap) {
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            preparing = preparing.replaceAll(val, key);
        }
        return preparing;
    }


    /**
     * 对特点类型进行格式化
     * 这里的String值由Mybatis输入的格式来解决决定的
     *
     * @return String
     */
    private static String quotationTypeFormat(String[] parametersTypeOrValue) {
        String[] d = {"String", "Timestamp", "Date", "Time", "LocalDate", "LocalTime", "LocalDateTime"};
        for (String s : d) {
            if (s.equals(parametersTypeOrValue[1])) {
                return String.format("'%s'", parametersTypeOrValue[0]);
            }
        }
        return parametersTypeOrValue[0];
    }

    /**
     * 特殊字符串类型进行转义
     * 由于一些JSON测试中只能对单引号进行处理，否者无法保证最后的值是能正确转义的
     * 占时保留代码,后续在考虑如何解决问题
     *
     * @return String
     */
    private static String[] specialTypesOfEscapeFormat(String[] parametersTypeOrValue) {
        final String type = parametersTypeOrValue[1];
        final String value = parametersTypeOrValue[0];
        String s = value;
        if ("String".equals(type)) {
            final Matcher matcher1 = PSingleQuotationMark.matcher(value);
            s = matcher1.replaceAll("\\\\'");
        }
        return new String[]{s, type};
    }

    /**
     * 获取到参数值与类型
     *
     * @param s 参数
     * @return String[0]=值 String[1]=类型
     */
    private static String[] getParametersTypeOrValue(String s) {
        char c = '(';
        if (s.endsWith(")")) {
            final char[] value = s.toCharArray();
            int find = -1;
            for (int j = value.length - 1; j >= 0; j--) {
                if (value[j] == c) {
                    find = j;
                    break;
                }
            }
            if (find >= 0) {
                final String val = s.substring(0, find);
                final String type = s.substring(find + 1, value.length - 1);
                return new String[]{val, type};
            }
        }
        return new String[]{s, ""};
    }
}
