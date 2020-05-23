package gyx.travel.service.impl;

import gyx.travel.dao.UserDao;
import gyx.travel.dao.impl.UserDaoImpl;
import gyx.travel.domain.User;
import gyx.travel.service.UserService;
import gyx.travel.util.MailUtils;
import gyx.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao dao=new UserDaoImpl();
    /**
     * 注册用户**/
    @Override
    public boolean regist(User user) {
        User u=dao.findByUsername(user.getUsername());
        if(u!=null)
            return false;
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        dao.save(user);
        String content="<a href='http://localhost:8080/maven_web2/user/active?code="+user.getCode()+"'>点击激活【潍泷旅游网】账户</a>" +
                "<p>如果不是本人注册，请忽略</p>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    public boolean active(String code){
        //1根据激活码查询用户对象
        User user=dao.findByCode(code);
        if(user!=null){
            //2调用dao的修改激活状态
            dao.updateStatus(user);
            return true;
        }
        return false;
    }

    public User login(User user){
        return dao.findByUsernameAndPassword(
                user.getUsername(),
                user.getPassword());
    }
}
