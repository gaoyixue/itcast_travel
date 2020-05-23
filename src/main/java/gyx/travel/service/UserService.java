package gyx.travel.service;
import gyx.travel.domain.User;

public interface UserService {
    boolean regist(User user);
    boolean active(String code);

    User login(User user);
}
