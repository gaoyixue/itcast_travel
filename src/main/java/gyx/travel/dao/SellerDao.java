package gyx.travel.dao;
import gyx.travel.domain.Seller;

public interface SellerDao {
    //根据id查询
    public Seller findById(int id);
}
