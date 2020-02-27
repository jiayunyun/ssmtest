package service.impl;

import java.util.List;

import mapper.ConfigMapper;
import mapper.ConfigMapperCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import po.ConfigCustom;
import po.QueryConfigVo;
import service.ConfigService;

@Component("configServiceImpl")
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapperCustom configMapperCustom;

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public List<ConfigCustom> findConfigList(QueryConfigVo queryConfigVo) throws Exception {
        return configMapperCustom.findConfigList(queryConfigVo);
    }

    @Override
    public ConfigCustom findConfigById(String id) throws Exception {
        return null;
    }

    @Override
    public void updateConfig(String id, ConfigCustom ConfigCustom) throws Exception {
        if (id != null) {
            ConfigCustom.setUuid(id);
            configMapper.updateByPrimaryKey(ConfigCustom);
        }

    }

    @Override
    public void insertConfig(ConfigCustom ConfigCustom) throws Exception {
        configMapper.insert(ConfigCustom);
    }

    @Override
    public void deleteConfig(String uuid) throws Exception {
        configMapper.deleteByPrimaryKey(uuid);
    }


}
