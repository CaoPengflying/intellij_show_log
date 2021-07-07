package com.cpf.showlog;

import com.cpf.showlog.filter.ShowLogFilter;
import com.intellij.execution.filters.ConsoleFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * date 2021/7/2
 *
 * @author caopengflying
 */
public class ShowLogProvider implements ConsoleFilterProvider {
    @Override
    public Filter @NotNull [] getDefaultFilters(@NotNull Project project) {
        Filter filter = new ShowLogFilter(project);
        return new Filter[]{filter};
    }
}
