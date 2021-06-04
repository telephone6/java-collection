package cn.lxw.mysql.util;

import cn.lxw.mysql.MainApp;
import cn.lxw.mysql.anno.CompareIgnore;
import cn.lxw.mysql.anno.CompareInfo;
import cn.lxw.mysql.bean.CompareTableResult;
import cn.lxw.mysql.entity.ColumnProp;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 功能描述: <br>
 * 〈The core code of this module.The logic of comparing is here.〉
 * @Param:
 * @Return:
 * @Author: luoxw
 * @Date: 2021/6/4 18:32
 */
public class CompareUtil {

    private static final String NEXT_LINE = "\r\n";
    private static final String DOUBLE_NEXT_LINE = "\r\n\r\n";
    private static final String TRIPPLR_NEXT_LINE = "\r\n\r\n\r\n";

    /*
     * 功能描述: <br>
     * 〈compare results from two different databases and get the result message.〉
     * @Param: [firstMap, secondMap]
     * @Return: java.lang.String
     * @Author: luoxw
     * @Date: 2021/6/4 18:24
     */
    public static String compareDBInfo(Map<String, Map<String, ColumnProp>> firstMap, Map<String, Map<String, ColumnProp>> secondMap) {
        StringBuilder sb = new StringBuilder();
        // 第1个库的表 & 第2个库的表：获得第2个库缺少的表或缺少的表字段、字段上的属性差异
        // compare A with B ,get the differences in B standing on A.
        CompareTableResult firstCompareTableResult = compareTable(firstMap, secondMap);
        // 第2个库的表 & 第1个库的表：获得第1个库缺少的表或缺少的表字段、字段上的属性差异
        // compare B with A ,get the differences in A standing on B.
        CompareTableResult secondCompareTableResult = compareTable(secondMap, firstMap);
        // 获取第1个数据库的缺少表
        // get the missing tables of A
        List<String> firstLackTableList = secondCompareTableResult.getLackTableList();
        if (firstLackTableList.size() > 0) {
            sb.append("第1个数据库缺少的表：").append(NEXT_LINE);
            sb.append(appendInForeach(firstLackTableList));
            sb.append(TRIPPLR_NEXT_LINE);
        }
        // 获取第2个数据库的缺少表
        // get the missing tables of B
        List<String> secondLackTableList = firstCompareTableResult.getLackTableList();
        if (secondLackTableList.size() > 0) {
            sb.append("第2个数据库缺少的表：").append(NEXT_LINE);
            sb.append(appendInForeach(secondLackTableList));
            sb.append(TRIPPLR_NEXT_LINE);
        }
        // 获取第1个数据库的缺少字段
        // get the missing columns of table in A
        Map<String, List<String>> firstTblLackColMap = secondCompareTableResult.getTblLackColMap();
        firstTblLackColMap.forEach((k, v) -> {
            if (v.size() > 0) {
                sb.append("第1个数据库的表[").append(k).append("]缺少的字段有:").append(NEXT_LINE);
                sb.append(appendInForeach(v));
            }
        });
        sb.append(firstTblLackColMap.size() > 0 ? TRIPPLR_NEXT_LINE : "");
        // 获取第2个数据库的缺少字段
        // get the missing columns of table in B
        Map<String, List<String>> secondTblLackColMap = firstCompareTableResult.getTblLackColMap();
        secondTblLackColMap.forEach((k, v) -> {
            if (v.size() > 0) {
                sb.append("第2个数据库的表[").append(k).append("]缺少的字段有:").append(NEXT_LINE);
                for (int i = 0; i < v.size(); i++) {
                    sb.append(appendInForeach(v));
                }
            }
        });
        sb.append(secondTblLackColMap.size() > 0 ? TRIPPLR_NEXT_LINE : "");
        // 获取第1个数据库的各表字段差异
        // get the different columns of table in A
        Map<String, Map<String, List<String>>> firstTblColDiffMap = firstCompareTableResult.getTblColDiffMap();
        firstTblColDiffMap.forEach((tableName, colDiffMap) -> {
            sb.append("第1个数据库的表[").append(tableName).append("]的字段差异有:").append(NEXT_LINE);
            sb.append(getColDiffStr(colDiffMap));
        });
        sb.append(firstTblColDiffMap.size() > 0 ? TRIPPLR_NEXT_LINE : "");
        // 获取第2个数据库的各表字段差异
        // get the different columns of table in B
        Map<String, Map<String, List<String>>> secondTblColDiffMap = secondCompareTableResult.getTblColDiffMap();
        secondTblColDiffMap.forEach((tableName, colDiffMap) -> {
            sb.append("第2个数据库的表[").append(tableName).append("]的字段差异有:").append(NEXT_LINE);
            sb.append(getColDiffStr(colDiffMap));
        });
        // finished ,return message
        return sb.toString();
    }

    /**
     * 功能描述: <br>
     * 〈获取不同字段的差异信息字符串〉
     * 〈Join the message by traversing the map.〉
     *
     * @Param: [colDiffMap]
     * @Return: java.lang.String
     * @Author: luoxw
     * @Date: 2021/6/4 15:08
     */
    private static String getColDiffStr(Map<String, List<String>> colDiffMap) {
        StringBuilder sb = new StringBuilder();
        colDiffMap.forEach((k, v) -> {
            if (v.size() > 0) {
                sb.append("字段[").append(k).append("]").append("差异如下：").append(NEXT_LINE);
                sb.append(appendInForeach(v));
                sb.append(DOUBLE_NEXT_LINE);
            }
        });
        sb.append(NEXT_LINE);
        return sb.toString();
    }

    /**
     * 功能描述: <br>
     * 〈数组遍历添加到字符串〉
     * 〈Join the message by traversing the array.〉
     *
     * @Param: [v]
     * @Return: java.lang.String
     * @Author: luoxw
     * @Date: 2021/6/4 15:09
     */
    private static <T> String appendInForeach(List<T> v) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < v.size(); i++) {
            sb.append(v.get(i));
            if (i != v.size() - 1) {
                sb.append(NEXT_LINE);
            }
        }
        return sb.toString();
    }

    /**
     * 功能描述: <br>
     * 〈获取数据库不同的表比较结果〉
     * get the differences in secondMap standing on firstMap.
     *
     * @Param: [firstMap, secondMap]
     * @Return: cn.lxw.mysql.bean.CompareTableResult
     * @Author: luoxw
     * @Date: 2021/6/4 15:09
     */
    private static CompareTableResult compareTable(Map<String, Map<String, ColumnProp>> firstMap, Map<String, Map<String, ColumnProp>> secondMap) {
        List<String> secondLackTableList = new ArrayList<>();
        Map<String, List<String>> secondTblLackColMap = new HashMap<>();
        Map<String, Map<String, List<String>>> secondTblColDiffMap = new HashMap<>();

        firstMap.forEach((firstTblName, firstColsMap) -> {
            Map<String, ColumnProp> secondColsMap = secondMap.get(firstTblName);
            //
            if (secondColsMap == null) {
                secondLackTableList.add(firstTblName);
                return;
            }

            Map<String, List<String>> colDiffMap = new HashMap<>();
            firstColsMap.forEach((firstColName, firstColProp) -> {
                ColumnProp secondColProp = secondColsMap.get(firstColName);
                if (secondColProp == null) {
                    List<String> secondTblLackColList = secondTblLackColMap.get(firstTblName);
                    if (secondTblLackColList == null) {
                        secondTblLackColList = new ArrayList<>();
                        secondTblLackColMap.put(firstTblName, secondTblLackColList);
                    }
                    secondTblLackColList.add(firstColName);
                    return;
                }
                // 比较每个属性
                try {
                    // compare the differences between two columns
                    List<String> colDiffList = compareColumnProp(firstColProp, secondColProp);
                    if (colDiffList.size() > 0) {
                        colDiffMap.put(firstColName, colDiffList);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            if (colDiffMap.size() > 0) {
                secondTblColDiffMap.put(firstTblName, colDiffMap);
            }
        });

        return new CompareTableResult() {
            {
                setLackTableList(secondLackTableList);
                setTblLackColMap(secondTblLackColMap);
                setTblColDiffMap(secondTblColDiffMap);
            }
        };
    }

    /**
     * 功能描述: <br>
     * 〈判断是是否需要进行比较〉
     * 〈Choose whether continue to compare or not.
     * If define the ignoreFieldNameMap,should judge the field is in the ignoreFieldNameMap or not.If it is,it can not go-on.
     * Then judge whether the annotation used by the field or not.If used,it can not go-on.
     * 〉
     *
     * @Param: [field]
     * @Return: boolean
     * @Author: luoxw
     * @Date: 2021/6/4 15:10
     */
    private static boolean canCompare(Field field) {
        if (MainApp.ignoreFieldNameMap.size() > 0) {
            return MainApp.ignoreFieldNameMap.get(field.getName()) == null;
        }
        return field.getAnnotation(CompareIgnore.class) == null;
    }

    /**
     * 功能描述: <br>
     * 〈比较列的详细信息〉
     * 〈compare the differences between two columns〉
     *
     * @Param: [firstColumnProp, secondColumnProp]
     * @Return: java.util.List<java.lang.String>
     * @Author: luoxw
     * @Date: 2021/6/4 15:10
     */
    private static List<String> compareColumnProp(ColumnProp firstColumnProp, ColumnProp secondColumnProp) throws IllegalAccessException {
        List<String> colDiffList = new ArrayList<>();
        Field[] fieldArr = ColumnProp.class.getDeclaredFields();
        for (Field field : fieldArr) {
            if (canCompare(field)) {
                CompareInfo compareInfoAnno = field.getAnnotation(CompareInfo.class);
                field.setAccessible(true);
                Object firstVal = field.get(firstColumnProp);
                Object secondVal = field.get(secondColumnProp);
                if (Objects.isNull(firstVal) && Objects.isNull(secondVal)) {
                    continue;
                }
                if (Objects.isNull(firstVal) && !Objects.isNull(secondVal)) {
                    colDiffList.add(new StringBuilder().append(compareInfoAnno.mean()).append(NEXT_LINE).append("+").append(secondVal).toString());
                }
                if (!Objects.isNull(firstVal) && Objects.isNull(secondVal)) {
                    colDiffList.add(new StringBuilder().append(compareInfoAnno.mean()).append(NEXT_LINE).append("-").append(secondVal).toString());
                }
                if (!Objects.isNull(firstVal) && !Objects.isNull(secondVal)) {
                    if (!firstVal.equals(secondVal)) {
                        colDiffList.add(new StringBuilder().append(compareInfoAnno.mean()).append(NEXT_LINE).append("*").append(firstVal).append("/").append(secondVal).toString());
                    }
                }
            }
        }
        return colDiffList;
    }
}
