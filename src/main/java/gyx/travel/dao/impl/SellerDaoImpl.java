package gyx.travel.dao.impl;
import gyx.travel.dao.SellerDao;
import gyx.travel.domain.Seller;
import gyx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findById(int id) {
        String sql="select * from tab_seller where sid=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),id);
    }


}
