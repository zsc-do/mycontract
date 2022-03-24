package cn.it.mycontract.controller.login;


import cn.hutool.db.Entity;
import cn.it.mycontract.entity.SysMenu;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.service.SysMenuService;
import cn.it.mycontract.service.SysUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
import java.util.HashMap;
import java.util.List;

@Controller
public class LoginController {


    @Autowired
    SysUserService sysUserService;


    @Autowired
    SysMenuService sysMenuService;







    @RequestMapping("/loginPage")
    public String loginPage(Model model,@RequestParam(value = "count",required = false) String count){
        model.addAttribute("count",count);
        return "login.html";
    }



    /**
     * 登录
     */
    @RequestMapping("/login")
    public String login(@RequestParam("account") String account,
                        @RequestParam("password") String password,
                        Model model,
                        HttpServletRequest request){

        if (loginLockCache.get(account) != null){


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = simpleDateFormat.format(new Date());


            String lockedTime = loginLockCache.get(account);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            Date d1 = null;
            Date d2 = null;
            try {
                d1 = df.parse(now);
                d2 = df.parse(lockedTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long diff = d1.getTime() - d2.getTime();
            long seconds = diff / (1000 * 60);

            if (seconds < 60){
                return "redirect:/loginPage?count=" + 0;
            }
        }

        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(account,password));

            HttpSession session = request.getSession();
            SysUser sysUser = sysUserService.selectLoginUser(account);
            session.setAttribute("sysUser", sysUser);


            List<SysMenu> menus = sysMenuService.getMenu(account);

            model.addAttribute("menus",menus);

            loginCountCache.remove(account);
            loginLockCache.remove(account);

            return  "index";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误!");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误!");
        }

        Integer loginCount = checkLoginCount(account);

        loginCount = 5 - loginCount;


        return "redirect:/loginPage?count=" + loginCount;
    }


    @RequestMapping("/mainPage")
    public String mainPage(){
        return "main.html";
    }



    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();//退出用户
        return "redirect:/loginPage";
    }



    public static HashMap<String,Integer> loginCountCache = new HashMap<>();

    public static HashMap<String,String> loginLockCache = new HashMap<>();

    private Integer checkLoginCount(String username){


        //如果登录错误超过5次，即提示不能再登录
        Integer timsold =(Integer) loginCountCache.get(username);

        if(null!=timsold && timsold>=4){
            loginCountCache.put(username, loginCountCache.get(username)+1);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(new Date());

            loginLockCache.put(username,format);
            return loginCountCache.get(username);
        }


        if(null == timsold){
            loginCountCache.put(username, 1);
            return loginCountCache.get(username);

        }

        if(timsold>=1 && timsold<=3){
            loginCountCache.put(username, loginCountCache.get(username)+1);
            return loginCountCache.get(username);
        }

        return loginCountCache.get(username);

    }

    public static void main(String[] args) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try
        {


            Date d1 = df.parse("2022-03-25 02:00:00");
            Date d2 = df.parse("");
            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60);

            System.out.println(days);

        }
        catch (Exception e)
        {
        }
    }


}
