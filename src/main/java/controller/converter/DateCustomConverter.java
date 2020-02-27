package controller.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;


//Converter<S, T> ：S表示源类型，T表示目标类型
//目标类型要和pojo接收参数的属性类型一致
public class DateCustomConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        //将日期串转成日期类型
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //如果转换成功直接 返回日期数据
            return simpleDateFormat.parse(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //没有转换成功，返回null
        return null;
    }

}
