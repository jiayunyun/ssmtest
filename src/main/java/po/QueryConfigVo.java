package po;

import java.util.List;


public class QueryConfigVo {

    //针对逆向工程生成的po类，不建议进行修改，建议定义扩展类
    private ConfigCustom ConfigCustom;

    //定义List<pojo>接收页面提交批量数据
    private List<ConfigCustom> configList;

    public ConfigCustom getConfigCustom() {
        return ConfigCustom;
    }

    public void setConfigCustom(ConfigCustom configCustom) {
        ConfigCustom = configCustom;
    }

    public List<ConfigCustom> getConfigList() {
        return configList;
    }

    public void setConfigList(List<ConfigCustom> configList) {
        this.configList = configList;
    }


}
