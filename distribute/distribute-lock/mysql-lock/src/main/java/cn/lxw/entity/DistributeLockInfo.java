package cn.lxw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 功能描述: <br>
 * 〈The entity of ditribute_lock_info table in database.〉
 * @Param:
 * @Return: {@link }
 * @Author: luoxw
 * @Date: 2021/7/26 20:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("distribute_lock_info")
public class DistributeLockInfo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("lock_key")
    private String lockKey;

    @TableField("lock_value")
    private String lockValue;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("expire_time")
    private LocalDateTime expireTime;

    @Override
    public String toString() {
        return "DistributeLockInfo{" +
                "id=" + id +
                ", lockKey='" + lockKey + '\'' +
                ", lockValue='" + lockValue + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", expireTime=" + expireTime +
                '}';
    }
}