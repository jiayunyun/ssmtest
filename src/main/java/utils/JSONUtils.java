package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanUtils;

public class JSONUtils {
    /**
     *
     * @author wangwei JSON工具类
     * @param
     *
     */

    /***
     * 将List对象序列化为JSON文本
     */
    public static <T> String toJSONString(List<T> list) {
        JSONArray jsonArray = JSONArray.fromObject(list);

        return jsonArray.toString();
    }

    /**
     * 从json HASH表达式中获取一个map，改map支持嵌套功能
     *
     * @param jsonString
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public static Map jsonToMap(String jsonString) {

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Iterator keyIter = jsonObject.keys();
        String key;
        Object value;
        Map valueMap = new HashMap();

        while (keyIter.hasNext()) {

            key = (String) keyIter.next();
            value = jsonObject.get(key).toString();
            valueMap.put(key, value);

        }

        return valueMap;
    }

    /**
     * 从json数组中得到相应java数组
     *
     * @param jsonString
     * @return
     */
    public static Object[] jsonToObjectArray(String jsonString) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);

        return jsonArray.toArray();

    }

    /***
     * 将对象序列化为JSON文本
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return jsonArray.toString();
    }

    /***
     * 将JSON对象数组序列化为JSON文本
     * @param jsonArray
     * @return
     */
    public static String toJSONString(JSONArray jsonArray) {
        return jsonArray.toString();
    }

    /***
     * 将JSON对象序列化为JSON文本
     * @param jsonObject
     * @return
     */
    public static String toJSONString(JSONObject jsonObject) {
        return jsonObject.toString();
    }

    /***
     * 将对象转换为List对象
     * @param object
     * @return
     */
    public static List toArrayList(Object object) {
        List arrayList = new ArrayList();

        JSONArray jsonArray = JSONArray.fromObject(object);

        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();

            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                Object key = keys.next();
                Object value = jsonObject.get(key);
                arrayList.add(value);
            }
        }

        return arrayList;
    }

    /***
     * 将对象转换为Collection对象
     * @param object
     * @return
     */
    public static Collection toCollection(Object object) {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return JSONArray.toCollection(jsonArray);
    }

    /***
     * 将对象转换为JSON对象数组
     * @param object
     * @return
     */
    public static JSONArray toJSONArray(Object object) {
        return JSONArray.fromObject(object);
    }

    /***
     * 将对象转换为JSON对象
     * @param object
     * @return
     */
    public static JSONObject toJSONObject(Object object) {
        return JSONObject.fromObject(object);
    }

    /***
     * 将对象转换为HashMap
     * @param object
     * @return
     */
    public static HashMap toHashMap(Object object) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        JSONObject jsonObject = JSONUtils.toJSONObject(object);
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            data.put(key, value);
        }

        return data;
    }

    /***
     * 将对象转换为List>
     * @param object
     * @return
     */
    // 返回非实体类型(Map)的List
    public static List<Map<String, Object>> toList(Object object) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONArray jsonArray = JSONArray.fromObject(object);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Map<String, Object> map = new HashMap<String, Object>();
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object value = jsonObject.get(key);
                map.put((String) key, value);
            }
            list.add(map);
        }
        return list;
    }

    /***
     * 将JSON对象数组转换为传入类型的List
     * @param
     * @param jsonArray
     * @param objectClass
     * @return
     */
    public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass) {
        return JSONArray.toList(jsonArray, objectClass);
    }

    /***
     * 将对象转换为传入类型的List
     * @param
     * @param jsonArray
     * @param objectClass
     * @return
     */
    public static <T> List<T> toList(Object object, Class<T> objectClass) {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return JSONArray.toList(jsonArray, objectClass);
    }

    /***
     * 将JSON对象转换为传入类型的对象
     * @param
     * @param jsonObject
     * @param beanClass
     * @return
     */
    public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass) {
        return (T) JSONObject.toBean(jsonObject, beanClass);
    }

    /***
     * 将将对象转换为传入类型的对象
     * @param
     * @param object
     * @param beanClass
     * @return
     */
    public static <T> T toBean(Object object, Class<T> beanClass) {
        JSONObject jsonObject = JSONObject.fromObject(object);

        return (T) JSONObject.toBean(jsonObject, beanClass);
    }


    /***
     * 将JSON文本反序列化为主从关系的实体
     * @param 泛型T 代表主实体类型
     * @param 泛型D1 代表从实体类型
     * @param 泛型D2 代表从实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailName1 从实体类在主实体类中的属性
     * @param detailClass1 从实体类型
     * @param detailName2 从实体类在主实体类中的属性
     * @param detailClass2 从实体类型
     * @return
     */
    public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass,
                                       String detailName1, Class<D1> detailClass1, String detailName2,
                                       Class<D2> detailClass2) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
        JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);

        T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
        List<D1> detailList1 = JSONUtils.toList(jsonArray1, detailClass1);
        List<D2> detailList2 = JSONUtils.toList(jsonArray2, detailClass2);

        try {
            BeanUtils.setProperty(mainEntity, detailName1, detailList1);
            BeanUtils.setProperty(mainEntity, detailName2, detailList2);
        } catch (Exception ex) {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }

        return mainEntity;
    }

    public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass,
                                       String detailName1, Class<D1> detailClass1) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);

        T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
        List<D1> detailList1 = JSONUtils.toList(jsonArray1, detailClass1);

        try {
            BeanUtils.setProperty(mainEntity, detailName1, detailList1);
        } catch (Exception ex) {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }

        return mainEntity;
    }

    /***
     * 将JSON文本反序列化为主从关系的实体
     * @param 泛型T 代表主实体类型
     * @param 泛型D1 代表从实体类型
     * @param 泛型D2 代表从实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailName1 从实体类在主实体类中的属性
     * @param detailClass1 从实体类型
     * @param detailName2 从实体类在主实体类中的属性
     * @param detailClass2 从实体类型
     * @param detailName3 从实体类在主实体类中的属性
     * @param detailClass3 从实体类型
     * @return
     */
    public static <T, D1, D2, D3> T toBean(String jsonString,
                                           Class<T> mainClass, String detailName1, Class<D1> detailClass1,
                                           String detailName2, Class<D2> detailClass2, String detailName3,
                                           Class<D3> detailClass3) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
        JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
        JSONArray jsonArray3 = (JSONArray) jsonObject.get(detailName3);

        T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
        List<D1> detailList1 = JSONUtils.toList(jsonArray1, detailClass1);
        List<D2> detailList2 = JSONUtils.toList(jsonArray2, detailClass2);
        List<D3> detailList3 = JSONUtils.toList(jsonArray3, detailClass3);

        try {
            BeanUtils.setProperty(mainEntity, detailName1, detailList1);
            BeanUtils.setProperty(mainEntity, detailName2, detailList2);
            BeanUtils.setProperty(mainEntity, detailName3, detailList3);
        } catch (Exception ex) {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }

        return mainEntity;
    }

    /***
     * 将JSON文本反序列化为主从关系的实体
     * @param 主实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailClass 存放了多个从实体在主实体中属性名称和类型
     * @return
     */
    public static <T> T toBean(String jsonString, Class<T> mainClass,
                               HashMap<String, Class> detailClass) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
        for (Object key : detailClass.keySet()) {
            try {
                Class value = (Class) detailClass.get(key);
                BeanUtils.setProperty(mainEntity, key.toString(), value);
            } catch (Exception ex) {
                throw new RuntimeException("主从关系JSON反序列化实体失败！");
            }
        }
        return mainEntity;
    }

    /**
     * 从一个JSON 对象字符格式中得到一个java对象
     *
     * @param jsonString
     * @param beanCalss
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToBean(String jsonString, Class<T> beanCalss) {

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        T bean = (T) JSONObject.toBean(jsonObject, beanCalss);
        return bean;

    }

    /**
     * 将java对象转换成json字符串
     *
     * @param bean
     * @return
     */
    public static String beanToJson(Object bean) {

        JSONObject json = JSONObject.fromObject(bean);

        return json.toString();

    }

    /**
     * 将java对象转换成json字符串
     *
     * @param bean
     * @return
     */
    public static JSONObject beanToJsonObject(Object bean, String[] _nory_changes, boolean nory) {
        JSONObject json = null;
        if (null == bean) {
            json = new JSONObject();
            return json;
        }
        if (nory) {//转换_nory_changes里的属性

            Field[] fields = bean.getClass().getDeclaredFields();
            String str = "";
            for (Field field : fields) {
                str += (":" + field.getName());
            }
            fields = bean.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                str += (":" + field.getName());
            }
            str += ":";
            for (String s : _nory_changes) {
                str = str.replace(":" + s + ":", ":");
            }
            json = JSONObject.fromObject(bean, configJson(str.split(":")));

        } else {//转换除了_nory_changes里的属性
            json = JSONObject.fromObject(bean, configJson(_nory_changes));
        }
        return json;
    }

    /**
     * 将java对象转换成json字符串
     *
     * @param bean
     * @return
     */
    public static String beanToJson(Object bean, String[] _nory_changes, boolean nory) {
        return beanToJsonObject(bean, _nory_changes, nory).toString();
    }

    private static JsonConfig configJson(String[] excludes) {

        JsonConfig jsonConfig = new JsonConfig();

        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(false);

        return jsonConfig;

    }


    /**
     * 将java对象List集合转换成json字符串
     *
     * @param beans
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String beanListToJson(List beans) {

        StringBuffer rest = new StringBuffer();

        rest.append("[");

        int size = beans.size();

        for (int i = 0; i < size; i++) {

            rest.append(beanToJson(beans.get(i)) + ((i < size - 1) ? "," : ""));

        }

        rest.append("]");

        return rest.toString();

    }

    /**
     * @param beans
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String beanListToJson(List beans, String[] _nory_changes, boolean nory) {

        StringBuffer rest = new StringBuffer();

        rest.append("[");

        int size = beans.size();

        for (int i = 0; i < size; i++) {
            try {
                rest.append(beanToJson(beans.get(i), _nory_changes, nory));
                if (i < size - 1) {
                    rest.append(",");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        rest.append("]");

        return rest.toString();

    }


    /**
     * map集合转换成json格式数据
     *
     * @param map
     * @return
     */
    public static String mapToJson(Map<String, ?> map, String[] _nory_changes, boolean nory) {

        String s_json = "{";

        Set<String> key = map.keySet();
        for (Iterator<?> it = key.iterator(); it.hasNext(); ) {
            String s = (String) it.next();
            if (map.get(s) == null) {

            } else if (map.get(s) instanceof List<?>) {
                s_json += (s + ":" + JSONUtils.beanListToJson((List<?>) map.get(s), _nory_changes, nory));

            } else {
                JSONObject json = JSONObject.fromObject(map);
                s_json += (s + ":" + json.toString());
                ;
            }

            if (it.hasNext()) {
                s_json += ",";
            }
        }

        s_json += "}";
        return s_json;
    }


    public static String listToJson(List<?> list) {

        JSONArray jsonArray = JSONArray.fromObject(list);

        return jsonArray.toString();

    }

    /**
     * 从json对象集合表达式中得到一个java对象列表
     *
     * @param jsonString
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonToBeanList(String jsonString, Class<T> beanClass) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONObject jsonObject;
        T bean;
        int size = jsonArray.size();
        List<T> list = new ArrayList<T>(size);

        for (int i = 0; i < size; i++) {

            jsonObject = jsonArray.getJSONObject(i);
            bean = (T) JSONObject.toBean(jsonObject, beanClass);
            list.add(bean);

        }

        return list;

    }
}