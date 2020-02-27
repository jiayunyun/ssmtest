package mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import po.Config;
import po.ConfigExample;

public interface ConfigMapper {
    long countByExample(ConfigExample example);

    int deleteByExample(ConfigExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(Config record);

    int insertSelective(Config record);

    List<Config> selectByExample(ConfigExample example);

    Config selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") Config record, @Param("example") ConfigExample example);

    int updateByExample(@Param("record") Config record, @Param("example") ConfigExample example);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);
}