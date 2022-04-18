package cn.it.mycontract.mobile;


import cn.it.mycontract.entity.SysMenu;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.service.SysMenuService;
import cn.it.mycontract.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MobileLoginController {



    @Autowired
    SysUserService sysUserService;


    @Autowired
    SysMenuService sysMenuService;

    @RequestMapping("/mobile/loginPage")
    public String monileLoginPage(Model model){

        return "mobile/login.html";
    }



    /**
     * 登录
     */
    @RequestMapping("/mobile/login")
    public String login(@RequestParam("account") String account,
                        @RequestParam("password") String password,
                        Model model,
                        HttpServletRequest request){


        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(account,password));

            HttpSession session = request.getSession();
            SysUser sysUser = sysUserService.selectLoginUser(account);
            session.setAttribute("sysUser", sysUser);


            List<SysMenu> menus = sysMenuService.getMenu(account);

            model.addAttribute("menus",menus);


            return  "/mobile/mhtsq.html";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误!");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误!");
        }


        return "redirect:/mobile/loginPage";
    }


    /**
     * 退出登录
     */
    @RequestMapping("/mobile/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();//退出用户
        return "redirect:/mobile/loginPage";
    }
}
