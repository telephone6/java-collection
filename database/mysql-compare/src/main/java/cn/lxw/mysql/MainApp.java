package cn.lxw.mysql;

import cn.lxw.mysql.entity.ColumnProp;
import cn.lxw.mysql.util.CompareUtil;
import cn.lxw.mysql.util.MySQLUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MainApp {
    // info of database A
    private final static String A_HOST = "192.168.0.64:3306";
    private final static String A_DBNAME = "rcs_info";
    private final static String A_USERNAME = "rcs_user";
    private final static String A_USERPWD = "rcs@202103291104.";
    // info of database B
    private final static String B_HOST = "192.168.0.64:3306";
    private final static String B_DBNAME = "rcs_info_test";
    private final static String B_USERNAME = "rcs_user_test";
    private final static String B_USERPWD = "rcs@202103291104.";
    // help you define which field u want to ignore in cn.lxw.mysql.entity.ColumnProp
    public static volatile Map<String,String> ignoreFieldNameMap = new ConcurrentHashMap<>();

    /**
     * 功能描述: <br>
     * 〈Start Here〉
     * @Param: [args]
     * @Return: void
     * @Author: luoxw
     * @Date: 2021/6/4 15:10
     */
    public static void main(String[] args) throws Exception {
//        ignoreMap.put("ordinalPosition","ordinalPosition");
        Map<String, Map<String, ColumnProp>> dbAInfo = MySQLUtil.getDBInfo(A_HOST, A_DBNAME, A_USERNAME, A_USERPWD);
        Map<String, Map<String, ColumnProp>> dbBInfo = MySQLUtil.getDBInfo(B_HOST, B_DBNAME, B_USERNAME, B_USERPWD);

        String compareResult = CompareUtil.compareDBInfo(dbAInfo, dbBInfo);
        System.out.println(compareResult);
    }

}
