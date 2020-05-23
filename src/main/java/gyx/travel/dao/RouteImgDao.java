package gyx.travel.dao;

import gyx.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    //根据rid查询图片集合
    public List<RouteImg> findByRid(int rid);
}
