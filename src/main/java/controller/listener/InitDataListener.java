package controller.listener;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;
import po.ConfigCustom;
import po.QueryConfigVo;
import service.ConfigService;

public class InitDataListener implements InitializingBean, ServletContextAware {

    @Resource
	private ConfigService configService;
	
    @Override
    public void setServletContext(ServletContext arg0) {
		QueryConfigVo queryConfigVo = new QueryConfigVo();
		List<ConfigCustom> configlist = null;
		try {
		    configlist = configService.findConfigList(queryConfigVo);
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		arg0.setAttribute("configlist", configlist);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub

    }

}
