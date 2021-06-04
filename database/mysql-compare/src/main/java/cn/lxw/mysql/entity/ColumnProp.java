package cn.lxw.mysql.entity;

import cn.lxw.mysql.anno.CompareIgnore;
import cn.lxw.mysql.anno.CompareInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈The entity of information_schema.COLUMNS〉
 * @Param:
 * @Return:
 * @Author: luoxw
 * @Date: 2021/6/4 18:25
 */
public class ColumnProp {
    /**
     * table_catalog
     */
    @CompareIgnore
    @CompareInfo(mean = "表目录")
    private String tableCatalog;

    /**
     * table_schema
     */
    @CompareIgnore
    @CompareInfo(mean = "库名")
    private String tableSchema;

    /**
     * table_name
     */
    @CompareIgnore
    @CompareInfo(mean = "表名")
    private String tableName;

    /**
     * column_name
     */
    @CompareInfo(mean = "列名")
    private String columnName;

    /**
     * ordinal_position
     */
    @CompareInfo(mean = "位置")
    @CompareIgnore
    private Long ordinalPosition;

    /**
     * column_default
     */
    @CompareIgnore
    @CompareInfo(mean = "默认列")
    private String columnDefault;

    /**
     * is_nullable
     */
    @CompareInfo(mean = "是否可空")
    private String isNullable;

    /**
     * data_type
     */
    @CompareInfo(mean = "类型")
    private String dataType;

    /**
     * character_maximum_length
     */
    @CompareIgnore
    @CompareInfo(mean = "最大字符长度")
    private Long characterMaximumLength;

    /**
     * character_octet_length
     */
    @CompareIgnore
    @CompareInfo(mean = "oct字符串长度")
    private Long characterOctetLength;

    /**
     * numeric_precision
     */
    @CompareInfo(mean = "整数精度")
    private Long numericPrecision;

    /**
     * numeric_scale
     */
    @CompareInfo(mean = "小数精度")
    private Long numericScale;

    /**
     * datetime_precision
     */
    @CompareIgnore
    @CompareInfo(mean = "时间精度")
    private Long datetimePrecision;

    /**
     * character_set_name
     */
    @CompareInfo(mean = "字符集")
    private String characterSetName;

    /**
     * collation_name
     */
    @CompareInfo(mean = "排序规则")
    private String collationName;

    /**
     * column_type
     */
    @CompareIgnore
    @CompareInfo(mean = "完整类型")
    private String columnType;

    /**
     * column_key
     */
    @CompareInfo(mean = "索引")
    private String columnKey;

    /**
     * extra
     */
    @CompareInfo(mean = "额外属性")
    private String extra;

    /**
     * privileges
     */
    @CompareIgnore
    @CompareInfo(mean = "用户权限")
    private String privileges;

    /**
     * column_comment
     */
    @CompareInfo(mean = "注释")
    private String columnComment;

    /**
     * generation_expression
     */
    @CompareIgnore
    @CompareInfo(mean = "表达式")
    private String generationExpression;

    public static Map<String, ColumnProp> getFromResult(final ResultSet rs) throws SQLException {
        if(rs == null){
            return null;
        }
        Map<String, ColumnProp> columnsMap = new HashMap<>();
        while (rs.next()) {
            ColumnProp columnProp = new ColumnProp(){
                {
                    //table_catalog
                    setTableCatalog(rs.getString("table_catalog"));
                    //table_schema
                    setTableSchema(rs.getString("table_schema"));
                    //table_name
                    setTableName(rs.getString("table_name"));
                    //column_name
                    setColumnName(rs.getString("column_name"));
                    //ordinal_position
                    setOrdinalPosition(rs.getLong("ordinal_position"));
                    //column_default
                    setColumnDefault(rs.getString("column_default"));
                    //is_nullable
                    setIsNullable(rs.getString("is_nullable"));
                    //data_type
                    setDataType(rs.getString("data_type"));
                    //character_maximum_length
                    setCharacterMaximumLength(rs.getLong("character_maximum_length"));
                    //character_octet_length
                    setCharacterOctetLength(rs.getLong("character_octet_length"));
                    //numeric_precision
                    setNumericPrecision(rs.getLong("numeric_precision"));
                    //numeric_scale
                    setNumericScale(rs.getLong("numeric_scale"));
                    //datetime_precision
                    setDatetimePrecision(rs.getLong("datetime_precision"));
                    //character_set_name
                    setCharacterSetName(rs.getString("character_set_name"));
                    //collation_name
                    setCollationName(rs.getString("collation_name"));
                    //column_type
                    setColumnType(rs.getString("column_type"));
                    //column_key
                    setColumnKey(rs.getString("column_key"));
                    //extra
                    setExtra(rs.getString("extra"));
                    //privileges
                    setPrivileges(rs.getString("privileges"));
                    //column_comment
                    setColumnComment(rs.getString("column_comment"));
                    //generation_expression
                    setGenerationExpression(rs.getString("generation_expression"));
                }
            };
            columnsMap.put(columnProp.getColumnName(), columnProp);
        }
        return columnsMap;
    }

    public ColumnProp() {}

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Long getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(Long ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Long getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public void setCharacterMaximumLength(Long characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
    }

    public Long getCharacterOctetLength() {
        return characterOctetLength;
    }

    public void setCharacterOctetLength(Long characterOctetLength) {
        this.characterOctetLength = characterOctetLength;
    }

    public Long getNumericPrecision() {
        return numericPrecision;
    }

    public void setNumericPrecision(Long numericPrecision) {
        this.numericPrecision = numericPrecision;
    }

    public Long getNumericScale() {
        return numericScale;
    }

    public void setNumericScale(Long numericScale) {
        this.numericScale = numericScale;
    }

    public Long getDatetimePrecision() {
        return datetimePrecision;
    }

    public void setDatetimePrecision(Long datetimePrecision) {
        this.datetimePrecision = datetimePrecision;
    }

    public String getCharacterSetName() {
        return characterSetName;
    }

    public void setCharacterSetName(String characterSetName) {
        this.characterSetName = characterSetName;
    }

    public String getCollationName() {
        return collationName;
    }

    public void setCollationName(String collationName) {
        this.collationName = collationName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getGenerationExpression() {
        return generationExpression;
    }

    public void setGenerationExpression(String generationExpression) {
        this.generationExpression = generationExpression;
    }
}
