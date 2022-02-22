package cn.it.mycontract.service.impl;

import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.entity.SysUserArea;
import cn.it.mycontract.mapper.SysAreaMapper;
import cn.it.mycontract.mapper.SysUserAreaMapper;
import cn.it.mycontract.service.SysAreaService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    SysUserAreaMapper sysUserAreaMapper;

    @Override
    public List<SysArea> selectAreaList() {

        List<SysArea> sysAreas = sysAreaMapper.selectAreaList();
        return sysAreas;
    }

    @Override
    @Transactional
    public void addArea(SysArea sysArea, String leaderId) {

        sysAreaMapper.insert(sysArea);

        SysUserArea sysUserArea = new SysUserArea();
        sysUserArea.setUserId(Integer.parseInt(leaderId));
        sysUserArea.setAreaId(sysArea.getId());
        sysUserAreaMapper.insert(sysUserArea);
    }

    @Override
    @Transactional
    public void updateArea(SysArea sysArea, String leaderId) {

        sysAreaMapper.update(sysArea,new EntityWrapper<SysArea>()
                .eq("id",sysArea.getId()));


        SysUserArea sysUserArea = new SysUserArea();
        sysUserArea.setUserId(Integer.parseInt(leaderId));
        sysUserArea.setAreaId(sysArea.getId());
        sysUserAreaMapper.insert(sysUserArea);


    }

    @Override
    public SysArea getMatchLeader(String sponsorId) {

        SysArea sysAreas = sysAreaMapper.getMatchLeader(sponsorId);

        return sysAreas;
    }
}
