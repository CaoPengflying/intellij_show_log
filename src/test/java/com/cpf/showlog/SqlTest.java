package com.cpf.showlog;

import com.cpf.showlog.util.KeyNameUtil;
import com.cpf.showlog.util.SqlProUtil;
import org.junit.Test;

/**
 * date 2021/7/5
 *
 * @author caopengflying
 */
public class SqlTest {

    @Test
    public void sqlFormat() throws Exception {
        String sql = "[2021/07/05 14:36:01 CST] [INFO] (log.go:34:1570490000000099    caopengfeideMacBook-Pro.local) [dbdao]  [SQL] SELECT `id`, `created_at`,`is_delete`,  `updated_at`, `status` FROM `a` WHERE (FIND_IN_SET(?,b) and status = ? and activity_start_time <= NOW() and activity_end_time >= NOW()) AND (is_delete = ?) AND (rule_type = ?) []interface {}{81759247053180500, 0, 0, 1} - took: 48.970738ms";
        String formatSql = SqlProUtil.restoreSql(KeyNameUtil.FILTER_KEY_SUFFIX, sql);

        System.out.println(formatSql);

    }
}
