package service.impl;

import mapper.UsersMapper;
import mapper.UsersMapperCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import po.UsersCustom;
import service.UserService;

import java.util.List;
import java.util.Map;

@Component("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapperCustom userMapperCustom;
    @Autowired
    private UsersMapper userMapper;

    @Override
    public Map<String, Object> checkLogin(Map<String, Object> map)
            throws Exception {

        return userMapperCustom.checkLogin(map);

    }
    @Override
    public void updatepsw(String username, String oldpsw, String newpsw) {
        userMapperCustom.updatepsw(username, oldpsw, newpsw);

    }
    @Override
    public List<UsersCustom> userlist() {
        return userMapperCustom.finduser();
    }
    @Override
    public void insertUser(UsersCustom userCustom) throws Exception {
        userMapper.insert(userCustom);

    }
    @Override
    public void deleteUser(String uuid) throws Exception {
        userMapper.deleteByPrimaryKey(Integer.parseInt(uuid));

    }
    @Override
    public void updateUser(String id, UsersCustom userCustom) throws Exception {
        userCustom.setId(Integer.parseInt(id));
        //使用下边的方法无法更新大文本类型
        userMapper.updateByPrimaryKey(userCustom);

    }
}
