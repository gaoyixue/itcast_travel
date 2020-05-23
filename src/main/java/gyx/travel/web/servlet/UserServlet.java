package gyx.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gyx.travel.domain.ResultInfo;
import gyx.travel.domain.User;
import gyx.travel.service.UserService;
import gyx.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();

    public void regist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        System.out.println(checkcode_server);
        System.out.println(check);
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            System.out.println(json);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        }

        //1获取数据
        Map<String, String[]> map = request.getParameterMap();
        System.out.println("ahahhaha");
        //2封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3调用servlet完成注册
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4响应结果
        if (flag) {
            //注册成功
            info.setFlag(true);
            info.setErrorMsg("注册cg！");
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }
        //将info对象序列化为json对
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        //2封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3调用Service查询
        UserService service = new UserServiceImpl();
        User u = service.login(user);

        ResultInfo info = new ResultInfo();
        //4判断用户对象是否为null
        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        //5用户未激活
        if (u != null && !"Y".equals(u.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("你尚未激活，请激活");
        }

        //6判断登陆成功
        if (u != null && "Y".equals(u.getStatus())) {
            request.getSession().setAttribute("user", u);
            info.setFlag(true);
        }

        //7响应回去的数据
//        ObjectMapper mapper = new ObjectMapper();
//        response.setContentType("application/json;charset=utf-8");
//        mapper.writeValue(response.getOutputStream(), info);
        writeValue(info,response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //从session中获取登录用户
        Object user = request.getSession().getAttribute("user");
        System.out.println(user);

        //将user写回客户端
//        ObjectMapper map = new ObjectMapper();
//        response.setContentType("application/json;charset=utf-8");
//        map.writeValue(response.getOutputStream(), user);
        writeValue(user,response);
    }

    public void exit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1销毁session
        request.getSession().invalidate();
        //2跳转到登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    public void active(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null) {
            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            String msg = null;
            if (flag) {
                msg = "激活成功，请<a href='login.html'>登陆</a>";
            } else {
                //激活失败
                msg = "激活失败,请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
