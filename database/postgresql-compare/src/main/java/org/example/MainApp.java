package org.example;

import org.example.dto.PgCollection;
import org.example.entity.*;
import org.example.util.PgClassComparator;
import org.example.util.PgIndexComparator;
import org.example.util.PgUtil;
import org.example.util.ReflectUtil;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class MainApp {

    private static final String SOURCE_URL = "jdbc:postgresql://192.168.10.22:5432/test_db?allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8";

    private static final String SOURCE_USER = "root";

    private static final String SOURCE_PASSWORD = "123456";

    private static final String TARGET_URL = "jdbc:postgresql://192.168.10.55:5432/test_db?allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8";

    private static final String TARGET_USER = "root";

    private static final String TARGET_PASSWORD = "123456";

    private static final String SELECT_TABLES = "select * from pg_class";

    private static final String SELECT_DESCRIPTION = "select * from pg_description";

    private static final String SELECT_ATTRIBUTE = "select * from pg_attribute";

    private static final String SELECT_TYPE = "select * from pg_type";

    private static final String SELECT_INDEXES = "select * from pg_indexes";


    public static void main(String[] args) {
        PgCollection sourceCollection = getCollection(SOURCE_URL, SOURCE_USER, SOURCE_PASSWORD);
        PgCollection targetCollection = getCollection(TARGET_URL, TARGET_USER, TARGET_PASSWORD);
        // compare table index in database
        PgIndexComparator.compare(sourceCollection, targetCollection);
        // compare tables and columns in database
        PgClassComparator.compare(sourceCollection, targetCollection);
    }

    public static PgCollection getCollection(String url, String user, String password) {
        List<PgClass> pgClassList = null;
        List<PgDescription> pgDescriptionList = null;
        List<PgAttribute> pgAttributeList = null;
        List<PgType> pgTypeList = null;
        List<PgIndex> pgIndexList = null;

        try(Connection sourceConn = PgUtil.createConnPg(url, user, password);){
            List<Map<String, Object>> selectTableList = PgUtil.select(sourceConn, SELECT_TABLES);
            pgClassList = ReflectUtil.transfer2Object(selectTableList, PgClass.class);

            List<Map<String, Object>> selectDescriptionList = PgUtil.select(sourceConn, SELECT_DESCRIPTION);
            pgDescriptionList = ReflectUtil.transfer2Object(selectDescriptionList, PgDescription.class);

            List<Map<String, Object>> selectAttributeList = PgUtil.select(sourceConn, SELECT_ATTRIBUTE);
            pgAttributeList = ReflectUtil.transfer2Object(selectAttributeList, PgAttribute.class);

            List<Map<String, Object>> selectTypeList = PgUtil.select(sourceConn, SELECT_TYPE);
            pgTypeList = ReflectUtil.transfer2Object(selectTypeList, PgType.class);

            List<Map<String, Object>> selectIndexList = PgUtil.select(sourceConn, SELECT_INDEXES);
            pgIndexList = ReflectUtil.transfer2Object(selectIndexList, PgIndex.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return PgCollection.builder()
                .pgAttributeList(pgAttributeList)
                .pgClassList(pgClassList)
                .pgDescriptionList(pgDescriptionList)
                .pgIndexList(pgIndexList)
                .pgTypeList(pgTypeList)
                .build();

    }

}
