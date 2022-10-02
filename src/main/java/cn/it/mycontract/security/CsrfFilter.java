package cn.it.mycontract.security;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CsrfFilter{

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 获取访问的路径，例如：login.jsp
        String requestURI = request.getRequestURI();
        if ("".equals(StringUtils.replace(requestURI, request.getContextPath() + "/", ""))) {
            chain.doFilter(request, response);
            return;
        }

        //css,js,image等不进行过滤
//        if (StringUtils.contains(requestURI, ".css")
//                || StringUtils.contains(requestURI, ".js")
//                || StringUtils.contains(requestURI, "js/")
//                || StringUtils.contains(requestURI, "img/")
//                || StringUtils.contains(requestURI, "static/")
//                || StringUtils.contains(requestURI, "mobile/")
//                || StringUtils.contains(requestURI, "/getHelp")
//                || StringUtils.contains(requestURI, "/queryMess")) {
//            chain.doFilter(request, response);
//            return;
//        }

        //获取请求服务地址
        String serverName = request.getServerName();
        String referer =request.getHeader("Referer");


        if ("127.0.0.1".equals(serverName)){
            chain.doFilter(request, response);
        }

        if (!StringUtils.isEmpty(referer)) {
            //同一平台进入与单点登录进入不进行拦截
            if (referer.indexOf(serverName) == -1) {

                response.setContentType("text/html;charset=utf-8");
                response.setStatus(403);
                PrintWriter out = response.getWriter();
                out.write("请勿非法请求[001]。");
                out.flush();
                out.close();
                return;
            }
        }

        chain.doFilter(request, response);
    }
}