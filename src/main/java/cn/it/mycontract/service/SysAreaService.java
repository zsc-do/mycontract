package cn.it.mycontract.service;

import cn.it.mycontract.entity.SysArea;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-13
 */
public interface SysAreaService extends IService<SysArea> {

    List<SysArea> selectAreaList();

}
