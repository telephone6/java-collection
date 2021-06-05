package cn.lxw.mysql.anno;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 功能描述: <br>
 * 〈Show the field means in Chinese,you can alse change it in cn.lxw.mysql.entity.ColumnProp 〉
 *   {@link cn.lxw.mysql.entity.ColumnProp}
 * @Param:
 * @Return:
 * @Author: luoxw
 * @Date: 2021/6/4 18:27
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface CompareInfo {
    public String mean() default "";
}
