package gyx.travel.service.impl;

import gyx.travel.dao.CategoryDao;
import gyx.travel.dao.impl.CategoryDaoImpl;
import gyx.travel.domain.Category;
import gyx.travel.service.CategoryService;
import gyx.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao=new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //1从redis中查询
        Jedis jedis= JedisUtil.getJedis();
        //2判断查询的集合是否为空
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);


        List<Category> cs=null;
        //3为空需要从数据库中查询，将数据存入redis
        if(categorys==null||categorys.size()==0){
            System.out.println("从数据库查询....");
            cs=categoryDao.findAll();
            for(int i=0;i<cs.size();i++){
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else{
            System.out.println("从redis中查询....");
            //4不空直接从redis中读取
            cs=new ArrayList<Category>();
            for(Tuple t:categorys){
                Category cate=new Category();
                cate.setCname(t.getElement());
                cate.setCid((int)t.getScore());
                cs.add(cate);
            }
        }
        return cs;
    }
}
