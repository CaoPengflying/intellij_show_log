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
        String sql = "[2021/07/05 14:36:01 CST] [INFO] (log.go:34:1570490000000099    caopengfeideMacBook-Pro.local) [dbdao]  [SQL] SELECT `id`, `activity_end_time`, `activity_id`, `activity_intro`, `activity_name`, `activity_start_time`, `activity_type`, `course_type`, `created_at`, `course_cycle_type`, `grade`, `is_delete`, `course_cycle`, `subject_id`, `updated_at`, `status`, `course_cycle_max`, `limit_source_flag`, `source`, `creator_name`, `operator_name`, `remark`, `read_status`, `course_progress_begin`, `course_progress_compare_condition`, `course_progress_time_begin`, `course_progress_end`, `course_progress_time_end`, `rule_type`, `spu_ids` FROM `xes_monkey_shop_activity` WHERE (FIND_IN_SET(?,spu_ids) and status = ? and activity_start_time <= NOW() and activity_end_time >= NOW()) AND (is_delete = ?) AND (rule_type = ?) []interface {}{81759247053180500, 0, 0, 1} - took: 48.970738ms";
        String formatSql = SqlProUtil.restoreSql(KeyNameUtil.FILTER_KEY_SUFFIX, sql);

        System.out.println(formatSql);

    }
}
