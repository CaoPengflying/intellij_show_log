package com.cpf.showlog.action.gui;

import com.cpf.showlog.util.ConfigUtil;
import com.cpf.showlog.util.KeyNameUtil;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 过滤设置 窗口
 *
 * @author lk
 * @version 1.0
 * @date 2020/8/23 17:14
 */
public class FilterSetting extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField sqlTextField;
    private JCheckBox startupCheckBox;

    /**
     * 窗口初始化
     *
     * @param project 项目
     */
    public FilterSetting(Project project) {
        //设置标题
        this.setTitle("Filter Setting");
        this.sqlTextField.setText(ConfigUtil.getFilterPre());
        int startup = PropertiesComponent.getInstance(project).getInt(KeyNameUtil.DB_STARTUP_KEY, 1);
        startupCheckBox.setSelected(startup == 1);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK(project));
        buttonCancel.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * 点击确认按钮处理
     *
     * @param project 项目
     */
    private void onOK(Project project) {
        String sqlTextFieldText = this.sqlTextField.getText();
        ConfigUtil.setFilterPre(sqlTextFieldText, KeyNameUtil.FILTER_KEY);
        ConfigUtil.setStartup(startupCheckBox.isSelected() ? 1 : 0);
        this.setVisible(false);
    }

    private void onCancel() {
        this.setVisible(false);
    }
}
