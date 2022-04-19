package cn.it.mycontract.controller.login;


import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class YZMController {

    /*
    * 登录模块生成验证码接口
    * */
    @GetMapping("/YZM/captcha")
    public void happyCaptcha(HttpServletRequest request, HttpServletResponse response){

        removeCaptcha(request);

        System.out.println("======生成一次验证码======");
        HappyCaptcha.require(request,response).type(CaptchaType.ARITHMETIC_ZH).build().finish();
    }


    @PostMapping("/YZM/verify")
    public Boolean verify(String code,HttpServletRequest request){
        //Verification Captcha
        boolean flag = HappyCaptcha.verification(request,code,true);

        return flag;

    }


    @GetMapping("/YZM/remove/captcha")
    public void removeCaptcha(HttpServletRequest request){
        HappyCaptcha.remove(request);
    }
}
