package service;

import java.util.List;

import javax.servlet.ServletContext;
import po.ConfigCustom;
import po.QueryConfigVo;

public interface ConfigService {

    public List<ConfigCustom> findConfigList(QueryConfigVo queryConfigVo) throws Exception;


    public ConfigCustom findConfigById(String id) throws Exception;


    public void updateConfig(String id, ConfigCustom ConfigCustom) throws Exception;

    public void insertConfig(ConfigCustom ConfigCustom) throws Exception;

    public void deleteConfig(String uuid) throws Exception;


}
