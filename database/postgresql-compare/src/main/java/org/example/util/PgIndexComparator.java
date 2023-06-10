package org.example.util;

import org.example.dto.PgCollection;
import org.example.entity.PgIndex;

public class PgIndexComparator {

    public static void compare(PgCollection source, PgCollection target){
        for (PgIndex sourcePgIndex : source.getPgIndexList()) {
            String sourceTableName = sourcePgIndex.getTablename();
            String sourceIndexName = sourcePgIndex.getIndexname();

            long count = target.getPgIndexList().stream().filter(s -> s.getTablename().equals(sourceTableName) && s.getIndexname().equals(sourceIndexName)).count();
            if(count < 1){
                System.out.println("目标库缺少表[" + sourceTableName + "]的索引[" + sourceIndexName + "], 对应索引创建语句[" + sourcePgIndex.getIndexdef() + "]");
            }

        }
    }

}
