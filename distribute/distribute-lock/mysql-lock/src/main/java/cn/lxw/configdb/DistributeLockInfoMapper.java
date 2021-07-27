package cn.lxw.configdb;


import cn.lxw.entity.DistributeLockInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 功能描述: <br>
 * 〈Judge by whether the record insert success or not to prove that lock-operation is whether success or not.So I need to define a method which can ignore insert when failed.〉
 * @Param:
 * @Return: {@link }
 * @Author: luoxw
 * @Date: 2021/7/26 20:19
 */
public interface DistributeLockInfoMapper extends BaseMapper<DistributeLockInfo> {

    int insertIgnore(DistributeLockInfo entity);

}
