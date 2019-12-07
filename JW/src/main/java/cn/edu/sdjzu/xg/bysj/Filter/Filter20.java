package cn.edu.sdjzu.xg.bysj.Filter;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(filterName = "Filter20", urlPatterns = {"/*"})
public class Filter20 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filter20 - judge begins");
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        //获得请求路径
        String path= httpServletRequest.getRequestURI();
        if (path.contains("Login")){
            //执行其他过滤器，若其他过滤器执行完毕则执行原请求
            chain.doFilter(request,response);
            System.out.println("直接执行下一个过滤器或原请求");
        }else {//若路径符合条件，则首先设置响应对象字符编码格式为utf8
            HttpSession session = httpServletRequest.getSession(false);
            JSONObject message = new JSONObject();
            if (session == null || session.getAttribute("currentUser") == null) {
                message.put("message", "您没有登录，请登录");
                response.getWriter().println(message);
            } else {
                //执行其他过滤器，若其他过滤器执行完毕则执行原请求
                chain.doFilter(request, response);
            }
        }
        System.out.println("Filter20 - judge ends");
    }
}
