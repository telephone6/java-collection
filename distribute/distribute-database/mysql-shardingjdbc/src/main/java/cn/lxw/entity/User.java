package cn.lxw.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_user")  //指定对应表
public class User {
    private Long userId;
    private String username;
    private String ustatus;
}
