package controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import po.ConfigCustom;
import po.QueryConfigVo;
import service.ConfigService;
import utils.GETUUIDBYRULES;

/**
 * 参数管理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/config")
public class ConfigController {

    public static final String separator = File.separator;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ConfigService configService;

    @RequestMapping("/tolist")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
        // 填充模型数据

        // 设置逻辑视图名
        modelAndView.setViewName("/config/configlist");
        return modelAndView;
    }

    @RequestMapping("/tocount")
    public ModelAndView tocount() {
        ModelAndView modelAndView = new ModelAndView();
        // 填充模型数据

        // 设置逻辑视图名
        modelAndView.setViewName("/count/count");
        return modelAndView;
    }
    
    @RequestMapping("/tofence")
    public ModelAndView tofence() {
        ModelAndView modelAndView = new ModelAndView();
        // 填充模型数据

        // 设置逻辑视图名
        modelAndView.setViewName("/fence/content");
        return modelAndView;
    }
    
    @RequestMapping("/tofence1")
    public ModelAndView tofence1() {
        ModelAndView modelAndView = new ModelAndView();
        // 填充模型数据

        // 设置逻辑视图名
        modelAndView.setViewName("/fence/content");
        return modelAndView;
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

    @RequestMapping(value = "configlist", produces = "application/json; charset=utf-8")
    public @ResponseBody
    String configlist(String id, QueryConfigVo queryConfigVo) throws Exception {
        // 调用service查询人员列表
        List<ConfigCustom> configList = configService.findConfigList(queryConfigVo);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", configList);
        return JSON.toJSONString(map);
    }

    @RequestMapping("insert")
    public String insert(HttpSession session, HttpServletRequest request,
                         HttpServletResponse response, Model m) throws Exception {
        String uuid = GETUUIDBYRULES.getUUID();
        String name = request.getParameter("name");
        String path = request.getParameter("path");
        //path = path.replaceAll("\\\\", separator);
        String remork = request.getParameter("remork");
        String beizhu = request.getParameter("beizhu");
        ConfigCustom configCustom = new ConfigCustom();
        configCustom.setUuid(uuid);
        configCustom.setName(name);
        configCustom.setPath(path);
        configCustom.setRemork(remork);
        configCustom.setBeizhu(beizhu);
        configService.insertConfig(configCustom);
        //添加后重新给内存添加配置信息
        QueryConfigVo queryConfigVo = new QueryConfigVo();
        List<ConfigCustom> configList = configService.findConfigList(queryConfigVo);
        ServletContext sc = request.getSession().getServletContext();
        sc.setAttribute("configlist", configList);
        return "success";
    }

    @RequestMapping("update")
    public String update(HttpSession session, HttpServletRequest request,
                         HttpServletResponse response, Model m) throws Exception {
        String id = request.getParameter("uuid");
        String name = request.getParameter("name");
        String path = request.getParameter("path");
        //path = path.replaceAll("\\\\", separator);
        String remork = request.getParameter("remork");
        String beizhu = request.getParameter("beizhu");
        ConfigCustom configCustom = new ConfigCustom();
        configCustom.setName(name);
        configCustom.setPath(path);
        configCustom.setRemork(remork);
        configCustom.setBeizhu(beizhu);
        configService.updateConfig(id, configCustom);
        //修改后重新给内存添加配置信息
        QueryConfigVo queryConfigVo = new QueryConfigVo();
        List<ConfigCustom> configList = configService.findConfigList(queryConfigVo);
        ServletContext sc = request.getSession().getServletContext();
        sc.setAttribute("configlist", configList);
        return "success";
    }

    @RequestMapping("delete")
    public String delete(HttpSession session, HttpServletRequest request,
                         HttpServletResponse response, Model m) throws Exception {
        String id = request.getParameter("uuid");
        configService.deleteConfig(id);
        //删除后重新给内存添加配置信息
        QueryConfigVo queryConfigVo = new QueryConfigVo();
        List<ConfigCustom> configList = configService.findConfigList(queryConfigVo);
        ServletContext sc = request.getSession().getServletContext();
        sc.setAttribute("configlist", configList);
        return "success";
    }


    /**
     * 选择路径或文件
     *
     * @return
     */
    @RequestMapping("/selectFile")
    public @ResponseBody
    String selectFilesAndDir() {
        JFileChooser jfc = new JFileChooser();
        //设置当前路径为桌面路径,否则将我的文档作为默认路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        jfc.setCurrentDirectory(fsv.getHomeDirectory());
        //JFileChooser.FILES_AND_DIRECTORIES 选择路径和文件
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //弹出的提示框的标题
        jfc.showDialog(new JLabel(), "确定");
        //用户选择的路径或文件
        File file = jfc.getSelectedFile();
        //System.out.println(file);
        String filePath = file.getAbsolutePath();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("path", filePath);
        return JSON.toJSONString(map);
    }

    /**
     * 选择路径或文件
     *
     * @return
     */
    @RequestMapping("selectFile2")
    public File selectFilesAndDir2() {
        JFileChooser jfc = new JFileChooser();
        //设置当前路径为桌面路径,否则将我的文档作为默认路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        jfc.setCurrentDirectory(fsv.getHomeDirectory());
        //JFileChooser.FILES_AND_DIRECTORIES 选择路径和文件
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //弹出的提示框的标题
        jfc.showDialog(new JLabel(), "确定");
        //用户选择的路径或文件
        File file = jfc.getSelectedFile();
       // System.out.println(file);
        return file;
    }

    /**
     * 选择路径或文件
     *
     * @return
     */
    @RequestMapping("selectFile3")
    @ResponseBody
    public String selectFilesAndDir3() {
        JFileChooser jfc = new JFileChooser();
        jfc.setVisible(true);
        //设置当前路径为桌面路径,否则将我的文档作为默认路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        jfc.setCurrentDirectory(fsv.getHomeDirectory());
        //JFileChooser.FILES_AND_DIRECTORIES 选择路径和文件
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = jfc.showOpenDialog(jfc);
        String filePath = "";
        Map<String, Object> map = new HashMap<String, Object>();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = jfc.getSelectedFile().getAbsolutePath();
            //System.out.println(filePath);
            map.put("path", filePath);
        }
        return JSON.toJSONString(map);
    }


}
