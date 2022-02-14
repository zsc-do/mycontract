package cn.it.mycontract.mapper;

import cn.it.mycontract.entity.SysArea;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-13
 */
public interface SysAreaMapper extends BaseMapper<SysArea> {

    List<SysArea> selectAreaList();

}
