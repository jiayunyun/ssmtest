package mapper;

import java.util.List;
import java.util.Map;
import po.UsersCustom;

public interface UsersMapperCustom {
    //
    //public List<UsersCustom> checkLogin(QueryUsersVo queryUsersVo) throws Exception;

    public Map<String, Object> checkLogin(Map<String, Object> map) throws Exception;

    
    public void updatepsw(String username, String oldpsw, String newpsw);
    
    public List<UsersCustom> finduser();
    
}