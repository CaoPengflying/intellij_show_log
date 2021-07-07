package com.cpf.showlog.util;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

/**
 * 配置项参数,设置持久化
 *
 * @author lk
 * @version 1.0
 * @date 2020/8/23 17:14
 */
public class ConfigUtil {

    /**
     * 显示窗口
     *
     * @param project
     */
    public static void setShowLog(Project project) {
        //查看配置文件 plugin.xml 中 toolWindow 的id
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Show log");
        if (toolWindow != null) {
            if (!toolWindow.isVisible()) {
                toolWindow.show(() -> {
                });
            }
            if (!toolWindow.isActive()) {
                //激活Restore Sql tab
                toolWindow.activate(() -> {
                });
            }
        }
    }


    /**
     * 获取关键字常量配置
     *
     * @return string
     */
    public static String getFilterPre() {
        return PropertiesComponent.getInstance().getValue(KeyNameUtil.DB_PREPARING_KEY, KeyNameUtil.FILTER_KEY);
    }

    public static void setFilterPre(String value, String defaultValue){
        PropertiesComponent.getInstance().setValue(KeyNameUtil.DB_PREPARING_KEY, null == value ? defaultValue : value);
    }


    /**
     * 设置启动过滤
     *
     * @param value   值
     */
    public static void setStartup(int value) {
        PropertiesComponent.getInstance().setValue(KeyNameUtil.DB_STARTUP_KEY, value, 1);
    }

    /**
     * 获取启动过滤
     *
     */
    public static boolean getStartup() {
        final int anInt = PropertiesComponent.getInstance().getInt(KeyNameUtil.DB_STARTUP_KEY, 1);
        return anInt == 1;
    }
}
