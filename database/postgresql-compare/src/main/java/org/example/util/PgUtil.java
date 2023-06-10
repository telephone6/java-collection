package org.example.util;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class PgUtil {

    public static Connection createConnPg(String url, String user, String password) {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static void closeConnPg(Connection c) {
        try {
            if(Objects.nonNull(c)){
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, Object>> select(Connection c, String sql) {
        List<Map<String, Object>> list = new ArrayList<>();

        try (
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                HashMap<String, Object> map = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String name = metaData.getColumnName(i);
                    Object object = rs.getObject(i);
                    map.put(name, object);
                }
                list.add(map);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
