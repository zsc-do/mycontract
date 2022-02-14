package cn.it.mycontract.service.impl;

import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.mapper.SysAreaMapper;
import cn.it.mycontract.service.SysAreaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-13
 */
@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements SysAreaService {


    @Autowired
    SysAreaMapper sysAreaMapper;
    @Override
    public List<SysArea> selectAreaList() {

        List<SysArea> sysAreas = sysAreaMapper.selectAreaList();
        return sysAreas;
    }
}
