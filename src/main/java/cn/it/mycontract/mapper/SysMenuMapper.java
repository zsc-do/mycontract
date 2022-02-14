package cn.it.mycontract.mapper;

import cn.it.mycontract.entity.SysMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengsc
 * @since 2022-01-30
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectMenu(String account);

    List<SysMenu> selectMenuList();

    SysMenu selectOneMenu(@Param("id") String id);

}
