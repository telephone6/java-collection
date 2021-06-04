package cn.lxw.mysql.util;

import cn.lxw.mysql.entity.ColumnProp;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述: <br>
 * 〈Some function about MySQL.Such as jdbc-connection,sql join...〉
 * @Param: 
 * @Return: 
 * @Author: luoxw
 * @Date: 2021/6/4 18:25
 */
public class MySQLUtil {
    public final static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [url, user, pwd]
     * @Return: java.sql.Connection
     * @Author: luoxw
     * @Date: 2021/6/4 18:25
     */
    public static Connection getMySQLConnection(String url, String user, String pwd) {
        Connection conn = null;
        try {
            Class.forName(MYSQL_DRIVER).newInstance();
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [host, dbName]
     * @Return: java.lang.String
     * @Author: luoxw
     * @Date: 2021/6/4 18:25
     */
    public static String getConnectUrl(String host, String dbName) {
        StringBuilder sb = new StringBuilder("jdbc:mysql://");
        sb.append(host).append("/").append(dbName).append("?useUnicode=true&useSSL=false&characterEncoding=utf8");
        return sb.toString();
    }

    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [dbName, tableName]
     * @Return: java.lang.String
     * @Author: luoxw
     * @Date: 2021/6/4 18:25
     */
    public static String getColumnsSQL(String dbName, String tableName) {
        StringBuilder sb = new StringBuilder("select * from information_schema.COLUMNS where TABLE_SCHEMA = '");
        sb.append(dbName).append("' and TABLE_NAME = '").append(tableName).append("'");
        return sb.toString();
    }

    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [host, dbName, userName, userPwd]
     * @Return: java.util.Map<java.lang.String,java.util.Map<java.lang.String,cn.lxw.mysql.entity.ColumnProp>>
     * @Author: luoxw
     * @Date: 2021/6/4 18:25
     */
    public static Map<String, Map<String, ColumnProp>> getDBInfo(String host, String dbName, String userName, String userPwd) throws Exception {
        Map<String, Map<String, ColumnProp>> tableMap = new ConcurrentHashMap<String, Map<String, ColumnProp>>();
        Connection conn = null;
        Statement tableStatement = null;
        ResultSet tableResultSet = null;
        try {
            conn = MySQLUtil.getMySQLConnection(MySQLUtil.getConnectUrl(host, dbName), userName, userPwd);
            tableStatement = conn.createStatement();
            tableResultSet = tableStatement.executeQuery("show tables");
            int tableColumnCount = tableResultSet.getMetaData().getColumnCount();
            while (tableResultSet.next()) {
                for (int i = 1; i <= tableColumnCount; i++) {
                    String tableName = tableResultSet.getString(i);
                    ResultSet columnResultSet = null;
                    Statement columnStatement = null;
                    try {
                        columnStatement = conn.createStatement();
                        columnResultSet = columnStatement.executeQuery(MySQLUtil.getColumnsSQL(dbName, tableName));
                        Map<String, ColumnProp> columnsMap = ColumnProp.getFromResult(columnResultSet);
                        tableMap.put(tableName, columnsMap);
                    }finally {
                        MySQLUtil.closeConnection(columnResultSet, columnStatement, null);
                    }
                }
            }
        } finally {
            MySQLUtil.closeConnection(tableResultSet, tableStatement, conn);
        }
        return tableMap;
    }


    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [rSet, psment, conn]
     * @Return: void
     * @Author: luoxw
     * @Date: 2021/6/4 18:29
     */
    public static void closeConnection(ResultSet rSet, Statement psment, Connection conn) throws Exception {
        try {
            if (rSet != null) {
                rSet.close();
                rSet = null;
            }
            if (psment != null) {
                psment.close();
                psment = null;
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            throw new Exception("Fail to close ResultSet / PreParedStatment / Connection !");
        }
    }
}
