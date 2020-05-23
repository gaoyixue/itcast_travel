package gyx.travel.web.servlet;

import gyx.travel.domain.PageBean;
import gyx.travel.domain.Route;
import gyx.travel.domain.User;
import gyx.travel.service.FavoriteService;
import gyx.travel.service.RouteService;
import gyx.travel.service.impl.FavoriteServiceImpl;
import gyx.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService service = new RouteServiceImpl();
    private FavoriteService fservice = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        int cid = 0;
        //2处理参数
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }

        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }

        //3调用service查询PageBean对象
        PageBean<Route> pb = service.pageQuery(cid, currentPage, pageSize);
        //4返回json序列化对象
        writeValue(pb, response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1接收id
        String rid = request.getParameter("rid");
        //2调用service查询route对象
        Route r = service.findOne(rid);
        //3转为json写回客户端
        writeValue(r, response);
    }

    //判断当前登录用户是否收藏该线路
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            //用户尚未登陆
            uid = 0;
        } else {
            uid = user.getUid();
        }
        //3调用favoriteService
        boolean flag = fservice.isFavorite(rid, uid);
        writeValue(flag, response);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            //用户尚未登陆
            return ;
        } else {
            uid = user.getUid();
        }
        //3调用service添加
        fservice.add(rid,uid);
    }
}
