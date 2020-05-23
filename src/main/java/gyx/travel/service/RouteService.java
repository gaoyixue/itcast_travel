package gyx.travel.service;
import gyx.travel.domain.PageBean;
import gyx.travel.domain.Route;

public interface RouteService {
    //根据类别进行分页查询
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize);
    public Route findOne(String rid);
}
