package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.json.JSONObject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


/**
 * 从内存中获取数据
 *
 * @author Administrator
 */
public class GetContext {

    /**
     * newfaces路径
     *
     * @return
     */
    public static String getnewfaces() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        Object obj = servletContext.getAttribute("configlist");
        List<JSONObject> jsonObjectList = JSONUtils.toJSONArray(obj);
        Map<String, String> map = new HashMap<String, String>();
        String Path = "";
        for (JSONObject jsonobj : jsonObjectList) {
            map = JSONUtils.toHashMap(jsonobj);
            String name = map.get("name");
            if (name == "newfaces" || "newfaces".equals(name)) {
                Path = map.get("path");
            }
        }
        return Path;
    }

}
