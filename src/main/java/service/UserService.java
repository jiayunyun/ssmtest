package service;

import po.UsersCustom;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 判断用户是否登陆
     *
     * @param map
     * @return
     * @throws Exception
     */
    public Map<String, Object> checkLogin(Map<String, Object> map) throws Exception;

    public void updatepsw(String username,String oldpsw,String newpsw);

    public List<UsersCustom> userlist();

    public void insertUser(UsersCustom userCustom) throws Exception;

    public void deleteUser(String uuid) throws Exception;

    public void updateUser(String id, UsersCustom userCustom) throws Exception;
}
