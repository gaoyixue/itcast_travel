package gyx.travel.domain;
import java.io.Serializable;

//收藏实体类
public class Favorite implements Serializable {
    private Route route;//旅游路线对象
    private String data;//收藏日期
    private User user;//用户

    public Favorite(){

    }
    public Favorite(Route route, String data, User user) {
        this.route = route;
        this.data = data;
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
