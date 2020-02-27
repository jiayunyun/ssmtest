package mapper;

import java.util.List;

import po.ConfigCustom;
import po.QueryConfigVo;

public interface ConfigMapperCustom {

    public List<ConfigCustom> findConfigList(QueryConfigVo queryConfigVo) throws Exception;

}
