package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.UserService;
import utils.MD5;

/**
 * <p>
 * Title: LoginController
 * </p>
 * <p>
 * Description:系统 登陆
 * </p>
 * <p>
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // 用户登陆，提交用户名称和用户密码
    @RequestMapping("/login")
    public ModelAndView loginsubmit(HttpSession session, String username,
                                    String password) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", MD5.getMD5(password));
        Map<String, Object> status = userService.checkLogin(params);
        ModelAndView modelAndView = new ModelAndView();
        if (status == null || status.size() < 1) {
            //return "redirect:/login";
            modelAndView.setViewName("login");
        } else {
            session.setAttribute("username", username);
            modelAndView.setViewName("main");
            //return "redirect:/faces/mian.action";
        }
        return modelAndView;
    }

    @RequestMapping(value="/checkpsw",method = RequestMethod.POST)
    @ResponseBody
    public String checkpsw(HttpServletRequest request) throws Exception {
    	String username = request.getParameter("username");
    	String password = request.getParameter("oldpsw");
    	 Map<String, Object> params = new HashMap<String, Object>();
         params.put("username", username);
         params.put("password", MD5.getMD5(password));
         Map<String, Object> status = userService.checkLogin(params);
         if (status == null || status.size() < 1) {
        	return "0";  //验证不正确
         }else {
        	 return "1";
         }
    }
    
    //修改密码
    @RequestMapping("/changepsw")
    @ResponseBody
    public String changepsw(HttpSession session,HttpServletRequest request) throws Exception {
    	String newpsw = request.getParameter("newpsw");
    	String oldpsw = request.getParameter("oldpsw");
    	String username = request.getParameter("username");
    	
    	userService.updatepsw(username, MD5.getMD5(oldpsw), MD5.getMD5(newpsw));
        // 重定向
        return "1";
    }
    // 用户退出
    @RequestMapping("/logout")
    public String logout(HttpSession session) throws Exception {

        // 清空session
        session.invalidate();
        // 重定向
        return "redirect:/";

    }

}
