package gyx.travel.service.impl;
import gyx.travel.dao.FavoriteDao;
import gyx.travel.dao.impl.FavoriteDaoImpl;
import gyx.travel.domain.Favorite;
import gyx.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao=new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite f=favoriteDao.findByRidAndUid(Integer.parseInt(rid),uid);
        return f!=null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(rid,uid);
    }
}
