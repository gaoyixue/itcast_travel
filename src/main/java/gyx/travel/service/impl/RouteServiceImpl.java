package gyx.travel.service.impl;

import gyx.travel.dao.FavoriteDao;
import gyx.travel.dao.RouteDao;
import gyx.travel.dao.RouteImgDao;
import gyx.travel.dao.SellerDao;
import gyx.travel.dao.impl.FavoriteDaoImpl;
import gyx.travel.dao.impl.RouteDaoImpl;
import gyx.travel.dao.impl.RouteImgDaoImpl;
import gyx.travel.dao.impl.SellerDaoImpl;
import gyx.travel.domain.PageBean;
import gyx.travel.domain.Route;
import gyx.travel.domain.RouteImg;
import gyx.travel.domain.Seller;
import gyx.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao dao=new RouteDaoImpl();
    private RouteImgDao routeImgDao=new RouteImgDaoImpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private FavoriteDao fdao=new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        PageBean<Route> pb=new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int totalCount=dao.findTotalCount(cid);
        pb.setTotalCount(totalCount);
        int start=(currentPage-1)*pageSize;
        List<Route> l=dao.findByPage(cid,start, pageSize);
        for(int i=0;i<l.size();i++)
            System.out.println(l.get(i).getRid());
        pb.setList(l);
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize+1);
        pb.setTotalPage(totalPage);
        return pb;
    }

    public Route findOne(String rid){
        //1根据rid去route表中查询route对象
        Route r=dao.findOne(Integer.parseInt(rid));
        //2根据rid查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(r.getRid());
        r.setRouteImgList(routeImgList);

        //3根据rid查询seller的对象
        Seller seller = sellerDao.findById(r.getSid());
        r.setSeller(seller);

        //4查询收藏次数
        int count=fdao.findCountByRid(r.getRid());
        r.setCount(count);
        return r;
    }

}
