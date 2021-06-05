package cn.lxw.mysql.util;

/**
 * 功能描述: <br>
 * 〈Utils of String operation.〉
 * @Param: 
 * @Return: 
 * @Author: luoxw
 * @Date: 2021/6/4 18:29
 */
public class StringUtil {
    
    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [str1, str2]
     * @Return: boolean
     * @Author: luoxw
     * @Date: 2021/6/4 18:24
     */
    public static boolean equal(String str1, String str2) {
        if (str1 == null & str2 == null) {
            return true;
        }
        if ((str1 == null && str2 != null)
                || (str1 != null & str2 ==null)) {
            return false;
        }
        return str1.trim().equals(str2.trim());
    }

    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [str1, str2]
     * @Return: boolean
     * @Author: luoxw
     * @Date: 2021/6/4 18:24
     */
    public static boolean notEqual(String str1, String str2) {
        return !equal(str1, str2);
    }

    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [str, array]
     * @Return: boolean
     * @Author: luoxw
     * @Date: 2021/6/4 18:25
     */
    public static boolean isIn(String str, String[] array) {
        for (String s : array) {
            if (equal(str, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean notIn(String str, String[] array) {
        return !isIn(str, array);
    }

    public static String sqlFilter(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll(".*([';]+|(--)+).*", "");
    }
}
