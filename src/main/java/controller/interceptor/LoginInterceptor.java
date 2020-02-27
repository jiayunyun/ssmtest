package controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class LoginInterceptor implements HandlerInterceptor {

    //handler：执行的Handler实例
    //在进入 Handler方法之前 去执行
    //执行场景：用于权限拦截、用户身份校验（校验用户是否登陆，如果没有登陆跳转登陆页面）
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //获取session
        HttpSession session = request.getSession();
        //从session中获取用户身份
        String username = (String) session.getAttribute("username");
        if (username != null) {
            //说明 用户身份合 法，放行
            return true;
        }
        //判断请求的url是否公开 地址（无需登陆即可访问的地址，系统 登陆地址），如果是公开 地址就放行
        //实际开发时公开 地址应该在配置文件中去配置
        //获取请求的url
        String url = request.getRequestURI();
        if (url.indexOf("login.action") >= 0) {
            //如果请求的地址是登陆地址（login.action）
            return true;
        }
        //跳转登陆页面
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        //如果false表示不放行，否则 放行
        return false;
    }

    //在进入 Handler方法之后，返回 modelAndView之前去执行
    //执行场景：处理一些公用的model模型数据，也可以处理公用的view（视图页面）
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    //在执行 Handler方法完成之后去执行此方法
    //执行场景：实现统一异常处理，实现统一日志处理
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
