package gyx.travel.dao;

import gyx.travel.domain.Favorite;

public interface FavoriteDao {
    public Favorite findByRidAndUid(int rid,int uid);

    public int findCountByRid(int rid);

    public void add(String rid, int uid);
}
