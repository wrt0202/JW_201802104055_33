package cn.edu.sdjzu.xg.bysj.controller.basic.degree;

import cn.edu.sdjzu.xg.bysj.domain.User;
import cn.edu.sdjzu.xg.bysj.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonNumber;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet( "/LoginController.ctl")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //创建Json对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            User loggedUser = UserService.getInstance().login(username,password);
            if(loggedUser!=null){
                message.put("message","登录成功");
                HttpSession session = request.getSession();
                //10分钟没有操作，使session失效
                session.setMaxInactiveInterval(10 * 60);
                session.setAttribute("currentUser",loggedUser);
                response.getWriter().println(message);
                //此处应重定向到菜单页
                return;
            }else {
                message.put("message","用户名或密码错误");
            }
        }catch (SQLException e){
            message.put("message","数据库操作异常");
        }catch (Exception e){
            message.put("message", "网络异常");
        }
        //响应message到前端
        response.getWriter().println(message);
    }
}
