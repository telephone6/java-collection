package cn.lxw.util;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.List;

/**
 * 功能描述: <br>
 * 〈Mix all util info〉
 * @Param:
 * @Return:
 * @Author: luoxw
 * @Date: 2021/5/19 14:03
 */
public class MixUtil {
    private static final long MB = 1024 * 1024L;
    
    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [mem]
     * @Return: java.lang.String
     * @Author: luoxw
     * @Date: 2021/5/19 14:03
     */
    private static String formatMemoryUsage(MemoryUsage mem) {
        return String.format("init: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                mem.getInit() / MB + "MB",
                mem.getMax() / MB + "MB", mem.getUsed() / MB + "MB",
                mem.getCommitted() / MB + "MB",
                mem.getUsed() * 100 / mem.getCommitted() + "%"
        );
    }

    public static String getMemoryInfo() {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        MemoryUsage headMemory = memory.getHeapMemoryUsage();
        MemoryUsage nonHeapMemory = memory.getNonHeapMemoryUsage();

        StringBuilder sb = new StringBuilder();
        sb.append("Heap Mem:").append("\n")
                .append(formatMemoryUsage(headMemory))
                .append("Non Heap Mem:").append("\n")
                .append(formatMemoryUsage(nonHeapMemory));
        return sb.toString();
    }

    public static String getGCInfo() {
        List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
        StringBuilder sb = new StringBuilder("GC Info:").append("\n");
        for (GarbageCollectorMXBean garbage : garbages) {
            String info = String.format("name: %s\t count:%s\t took:%s\t pool name:%s",
                    garbage.getName(),
                    garbage.getCollectionCount(),
                    garbage.getCollectionTime(),
                    Arrays.deepToString(garbage.getMemoryPoolNames()));
            sb.append(info).append("\n");
        }
        return sb.toString();
    }

    public static void printMemGCInfo() {
        String memoryInfo = getMemoryInfo();
        String gcInfo = getGCInfo();
        System.out.println(memoryInfo + gcInfo);
    }

    public static void printTip(String str) {
        System.out.println("=========================" + str +
                "===============================");
    }
}