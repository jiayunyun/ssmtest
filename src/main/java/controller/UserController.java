package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import po.QueryUsersVo;
import po.UsersCustom;
import service.UserService;
import utils.MD5;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	@RequestMapping("/tolist")
    public String anylises() {
        return "/user/userlist";
    }
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	@ResponseBody
	public String list(QueryUsersVo queryUsersVo) {
		List<UsersCustom> userlist = userservice.userlist();
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", userlist);
        return JSON.toJSONString(map);
	}
	
	@RequestMapping("insert")
    public String insert(HttpSession session, HttpServletRequest request,
                         HttpServletResponse response, Model m) throws Exception {
		Integer uuid = (int) (Math.random()*1000);//GETUUIDBYRULES.getUUID();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        UsersCustom userCustom = new UsersCustom();
        userCustom.setId(uuid);
        userCustom.setName(name);
        userCustom.setPassword(MD5.getMD5(password));
        userservice.insertUser(userCustom);
		return "success";
	}
	
	@RequestMapping("update")
    public String update(HttpSession session, HttpServletRequest request,
                         HttpServletResponse response, Model m) throws Exception {
		String uuid = request.getParameter("uuid");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        UsersCustom userCustom = new UsersCustom();
        userCustom.setId(Integer.parseInt(uuid));
        userCustom.setName(name);
        userCustom.setPassword(MD5.getMD5(password));
        userservice.updateUser(uuid, userCustom);
		return "success";
	}
	
	@RequestMapping("delete")
    public String delete(HttpSession session, HttpServletRequest request,
                         HttpServletResponse response, Model m) throws Exception {
        String uuid = request.getParameter("uuid");
        userservice.deleteUser(uuid);
        return "success";
	}

}
