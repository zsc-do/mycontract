package cn.it.mycontract.service;

import cn.it.mycontract.entity.SysMenu;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengsc
 * @since 2022-01-30
 */
public interface SysMenuService extends IService<SysMenu> {

    public List<SysMenu> getMenu(@Param("account") String account);

    List<SysMenu> selectMenusList();


    SysMenu selectOneMenu(String id);

}
