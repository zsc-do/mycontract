package cn.it.mycontract.controller.sys;


import cn.it.mycontract.entity.SysRole;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.service.SysUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengsc
 * @since 2022-01-30
 */
@Controller
public class SysUserController {


    @Autowired
    public SysUserService sysUserService;


    @RequestMapping("/queryUserPageList")
    public String queryUserPageList(Model model){

        List<SysUser> sysUsers = sysUserService.selectUserList();

        model.addAttribute("users",sysUsers);


        return "user/user-query";
    }


    @RequestMapping("/toAddUserPage")
    public String toAddUserPage(){
        return "user/user-add";
    }



    @RequestMapping("/addUser")
    public String addUser(@RequestParam("userName") String userName,
                          @RequestParam("userAccount") String userAccount,
                          @RequestParam("userPassword") String userPassword,
                          @RequestParam("userMobile") String userMobile,
                          @RequestParam("rolesId") String rolesId,
                          @RequestParam("areasId") String areasId){


        String[] rolesIdStr = rolesId.split(",");

        String[] areasIdStr = areasId.split(",");

        SysUser sysUser = new SysUser();
        sysUser.setAccount(userAccount);
        sysUser.setName(userName);
        sysUser.setPassword(userPassword);
        sysUser.setMobile(userMobile);
        sysUser.setDelFlag("0");


        sysUserService.addUser(sysUser,rolesIdStr,areasIdStr);



        return "redirect:/queryUserPageList";

    }



    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") String id){

        SysUser sysUser = new SysUser();
        sysUser.setDelFlag("1");
        sysUserService.update(sysUser,new EntityWrapper<SysUser>()
                .eq("id",id));




        return "redirect:/queryUserPageList";
    }



    @RequestMapping("/updateUserPage")
    public String updateUserPage(@RequestParam("id") String id,
                                 Model model){


        SysUser sysUser = sysUserService.selectById(id);


        model.addAttribute("user",sysUser);


        return "user/user-edit";
    }



    @RequestMapping("/updateUser")
    public String updateUser(@RequestParam("userId") String userId,
                             @RequestParam("username") String username,
                             @RequestParam("userAccount") String userAccount,
                             @RequestParam("userMobile") String userMobile,
                             @RequestParam("rolesId") String rolesId,
                             @RequestParam("areasId") String areasId){


        String[] rolesIdStr = rolesId.split(",");

        String[] areasIdStr = areasId.split(",");


        SysUser sysUser = new SysUser();
        sysUser.setAccount(userAccount);
        sysUser.setName(username);
        sysUser.setMobile(userMobile);
        sysUser.setId(Integer.parseInt(userId));


        sysUserService.updateUser(sysUser,rolesIdStr,areasIdStr);



        return "redirect:/queryUserPageList";
    }


    @ResponseBody
    @RequestMapping("/getAllUsers")
    public List<SysUser> getAllUsers(){
        List<SysUser> sysUsers = sysUserService.selectList(new EntityWrapper<SysUser>()
                .eq("del_flag",0));


        return sysUsers;
    }



    @ResponseBody
    @RequestMapping("/getUser")
    public SysUser getUser(HttpServletRequest request){

        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        return sysUser;
    }


}
