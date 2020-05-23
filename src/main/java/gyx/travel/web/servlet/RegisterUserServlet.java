package gyx.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gyx.travel.domain.ResultInfo;
import gyx.travel.domain.User;
import gyx.travel.service.UserService;
import gyx.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    private UserService service = new UserServiceImpl();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String check=request.getParameter("check");
        HttpSession session=request.getSession();
        String checkcode_server=(String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        System.out.println(checkcode_server);
        System.out.println(check);
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){
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

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }
}
