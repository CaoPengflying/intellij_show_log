package com.cpf.showlog.action;

import com.cpf.showlog.icons.ShowLogIcon;
import com.cpf.showlog.util.ConfigUtil;
import com.cpf.showlog.util.KeyNameUtil;
import com.cpf.showlog.util.PrintlnUtil;
import com.cpf.showlog.util.SqlProUtil;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * date 2021/7/2
 *
 * @author caopengflying
 */
public class RestoreSqlForSelection extends AnAction {
    public RestoreSqlForSelection() {
        super(ShowLogIcon.SHOW_LOG_ICON);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getProject();
        if (project == null) {
            return;
        }
        CaretModel caretModel = Objects.requireNonNull(e.getData(LangDataKeys.EDITOR)).getCaretModel();
        Caret currentCaret = caretModel.getCurrentCaret();
        String selectedText = currentCaret.getSelectedText();
        ConfigUtil.setShowLog(project);
        final String filterPre = ConfigUtil.getFilterPre();
        if (StringUtils.isNotEmpty(selectedText)) {
            //分割每一行
            if (isKeyWord(project, selectedText, filterPre)) {
                setFormatSelectedText(project, selectedText, filterPre);
            }
        }
    }

    private void setFormatSelectedText(Project project, String selectedRowText, String filterPre) {
        String sqlLine = "";
        //第一个关键字
        if (selectedRowText.contains(filterPre)) {
            sqlLine = selectedRowText;
        }
        if (StringUtils.isNotEmpty(sqlLine)) {
            //SQL还原
            String restoreSql = null;
            try {
                restoreSql = SqlProUtil.restoreSql(filterPre, sqlLine);
                //高亮显示
                PrintlnUtil.printlnSqlType(project, restoreSql);
            } catch (Exception e) {
                PrintlnUtil.println(project, KeyNameUtil.SQL_ERROR, ConsoleViewContentType.USER_INPUT, true);
            }

        } else {
            PrintlnUtil.println(project, "", ConsoleViewContentType.USER_INPUT);
            PrintlnUtil.println(project, KeyNameUtil.SQL_NULL, ConsoleViewContentType.USER_INPUT, true);
        }
    }

    private boolean isKeyWord(Project project, String selectedText, String filterPre) {
        Pattern sqlPattern = Pattern.compile(KeyNameUtil.SQL_REGEXP);
        Matcher matcher = sqlPattern.matcher(selectedText);
        if (StringUtils.isNotBlank(selectedText) && selectedText.contains(filterPre) && matcher.find()) {
            return true;
        }
        PrintlnUtil.println(project, "", ConsoleViewContentType.USER_INPUT);
        PrintlnUtil.println(project, KeyNameUtil.SQL_NULL, ConsoleViewContentType.USER_INPUT, true);
        return false;

    }


}
