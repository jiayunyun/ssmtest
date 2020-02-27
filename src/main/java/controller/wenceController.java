package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import po.Ai;
import utils.GetContext;
import utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/wence")
public class wenceController {
    public static final String separator = File.separator;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/toanylises")
    public String anylises() {
        return "/content/anylises";
    }

    /**
     * 	实时采集
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/images", method = RequestMethod.POST)
    @ResponseBody
    public String getImages(HttpServletRequest request) throws Exception {
        Query query = new Query();
        int page = 1;
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "go_time")));//时间倒序
        query.skip((page - 1) * 100);
        query.limit(50);
        List<Ai> ailist = mongoTemplate.find(query, Ai.class, "wence");
        String jsonstr = JSONUtils.listToJson(ailist);
        JSONArray jsonArray = JSONObject.parseArray(jsonstr);
        String wpath = GetContext.getnewfaces();
        wpath = wpath.replace("\\", "/");
        String cap_image_path = "";
        String t_image_path ="";
        if (jsonArray != null && !jsonArray.isEmpty()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                cap_image_path = jsonArray.getJSONObject(i).getString("cap_image_path").replace("\\", "/");//摄像机图片地址
                t_image_path = jsonArray.getJSONObject(i).getString("t_image_path").replace("\\", "/");//红外线热成像图片地址
                if (cap_image_path.contains(wpath)) {
                    String capimagepath = cap_image_path.replace(wpath, "/diku");
                    String timagepath = t_image_path.replace(wpath, "/diku");
                    jsonArray.getJSONObject(i).put("capimagepath", capimagepath);
                    jsonArray.getJSONObject(i).put("timagepath", timagepath);
                }
                String go_time = jsonArray.getJSONObject(i).getString("go_time");
                BigDecimal bd = new BigDecimal(go_time);
                jsonArray.getJSONObject(i).put("go_time", bd.toPlainString());
            }
        }
        return jsonArray.toJSONString();
    }


    @RequestMapping("/tosearch")
    public String toquanjing() {
        return "/wence/search";
    }

    @RequestMapping("/todaping")
    public String todaping() {
        return "/content/anylises";
    }
    /**
     * 	检 索
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public String search(HttpServletRequest request) throws Exception  {
        String pageNow = request.getParameter("page");
        Criteria c = new Criteria();
        int page = 1;
        if (pageNow != null && !"".equals(pageNow)) {
            page = Integer.parseInt(pageNow);
        }
        String code = request.getParameter("code");
        if (code != null && !"".equals(code)) {
            c.and("dir_path").is(code);
        }
        String t_ip = request.getParameter("t_ip");
        if (t_ip != null && !"".equals(t_ip)) {
            c.and("t_ip").is(t_ip);
        }
        String t_num = request.getParameter("t_num");
        if (t_num != null && !"".equals(t_num)) {
            c.and("t_num").gte(Double.parseDouble(t_num));
        }
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        if ((endtime != null && !"".equals(endtime)) && (starttime != null && !"".equals(starttime))) {
            starttime = getnumberfromstring(starttime);
            endtime = getnumberfromstring(endtime);
            c.andOperator(Criteria.where("go_time").gte(Double.parseDouble(starttime)).andOperator(Criteria.where("go_time").lte(Double.parseDouble(endtime))));
        }
        Query query = new Query(c);
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "go_time")));//时间倒序
        query.skip((page - 1) * 100);
        query.limit(100);
        List<Ai> ailist = mongoTemplate.find(query, Ai.class, "wence");
        Long totalcount = mongoTemplate.count(query, Ai.class, "wence");
        String jsonstr = JSONUtils.listToJson(ailist);
        JSONArray jsonArray = JSONObject.parseArray(jsonstr);
        String wpath = GetContext.getnewfaces();
        wpath = wpath.replace("\\", "/");
        String cap_image_path = "";
        String t_image_path ="";
        if (jsonArray != null && !jsonArray.isEmpty()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonArray.getJSONObject(i).put("total", totalcount);
                cap_image_path = jsonArray.getJSONObject(i).getString("cap_image_path").replace("\\", "/");//摄像机图片地址
                t_image_path = jsonArray.getJSONObject(i).getString("t_image_path").replace("\\", "/");//红外线热成像图片地址
                if (cap_image_path.contains(wpath)) {
                    String capimagepath = cap_image_path.replace(wpath, "/diku");
                    String timagepath = t_image_path.replace(wpath, "/diku");
                    jsonArray.getJSONObject(i).put("capimagepath", capimagepath);
                    jsonArray.getJSONObject(i).put("timagepath", timagepath);
                }
                String go_time = jsonArray.getJSONObject(i).getString("go_time");
                BigDecimal bd = new BigDecimal(go_time);
                jsonArray.getJSONObject(i).put("go_time", bd.toPlainString());
            }
        }
        return jsonArray.toJSONString();
    }
    /**
     * 从字符串中提取数字
     *
     * @param str
     * @return
     */
    public String getnumberfromstring(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String numstr = m.replaceAll("").trim();
        return numstr;
    }

    /**
     * 获取通道
     * @return
     */
    @RequestMapping("/dirpathinfo")
    @ResponseBody
    public String dirpathinfo() {
        return "";
    }


}
