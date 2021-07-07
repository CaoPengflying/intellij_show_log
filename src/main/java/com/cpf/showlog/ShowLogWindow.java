package com.cpf.showlog;

import com.cpf.showlog.console.ConsolePanel;
import com.cpf.showlog.icons.ShowLogIcon;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * date 2021/7/2
 *
 * @author caopengflying
 */
public class ShowLogWindow implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ConsolePanel consolePanel = new ConsolePanel();
        final JComponent jComponent = consolePanel.getConsolePanel(project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(jComponent, "", false);
        toolWindow.setIcon(ShowLogIcon.SHOW_LOG_ICON);
        toolWindow.getContentManager().addContent(content);
    }
}
