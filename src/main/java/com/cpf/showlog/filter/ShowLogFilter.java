package com.cpf.showlog.filter;

import com.cpf.showlog.util.ConfigUtil;
import com.cpf.showlog.util.KeyNameUtil;
import com.cpf.showlog.util.PrintlnUtil;
import com.cpf.showlog.util.SqlProUtil;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 语句过滤器
 *
 * @author lk
 * @version 1.0
 * @date 2020/4/10 22:00
 */
public class ShowLogFilter implements Filter {

    private final Project project;
    private static String preparingLine = "";

    public ShowLogFilter(Project project) {
        this.project = project;
    }

    @Nullable
    @Override
    public Result applyFilter(final String currentLine, int endPoint) {
        if (this.project == null) {
            return null;
        }
        final boolean startup = ConfigUtil.getStartup();
        if (startup && currentLine != null) {
            prints(currentLine, endPoint);
        }
        return null;
    }

    private void prints(final String currentLine, int endPoint) {
        final String sqlPre = ConfigUtil.getFilterPre();
        if (currentLine.contains(sqlPre)) {
            preparingLine = currentLine;
        }
        Pattern sqlPattern = Pattern.compile(KeyNameUtil.SQL_REGEXP);
        Matcher matcher = sqlPattern.matcher(preparingLine);
        if (StringUtils.isNotEmpty(preparingLine) && matcher.find()) {
            //序号前缀字符串
            String restoreSql = null;
            try {
                restoreSql = SqlProUtil.restoreSql(preparingLine);
                PrintlnUtil.println(project, KeyNameUtil.SQL_Line + restoreSql, ConsoleViewContentType.USER_INPUT);
                PrintlnUtil.printlnSqlType(project, restoreSql);
            } catch (Exception e) {
                e.printStackTrace();
            }

            preparingLine = "";
        }
    }
}
