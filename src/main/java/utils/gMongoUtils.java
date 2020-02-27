package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.JSON;  
//import com.alibaba.fastjson.JSONObject;  
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;


public class gMongoUtils  
{
    private final static String HOST = "127.0.0.1";// �˿�
    private final static int PORT = 27017;// �˿�
    private final static int POOLSIZE = 100;// ��������
    private final static int BLOCKSIZE = 300; // �ȴ����г���
    private static MongoClient mongo = null;
    private static DB db = null;
    private final static gMongoUtils instance = new gMongoUtils();
    private final static String databaseName = "cinding";

    /**
     * ʵ����
     *
     * @return
     * @throws Exception
     */
    private static gMongoUtils getInstance() throws Exception
    {
        return instance;
    }

    static
    {
        try
        {
            mongo = new MongoClient(new ServerAddress(HOST, PORT), getConfOptions());
            db = mongo.getDB(databaseName);
            // db.slaveOk();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static MongoClientOptions getConfOptions()
    {
        return new MongoClientOptions.Builder().socketKeepAlive(true) // �Ƿ񱣳ֳ�����
                .connectTimeout(500000) // ���ӳ�ʱʱ��
                .socketTimeout(500000) // read���ݳ�ʱʱ��
                .readPreference(ReadPreference.primary()) // ������Ȳ���?
                .connectionsPerHost(POOLSIZE) // ÿ����ַ���������?
                .maxWaitTime(1000 * 60 * 50) // �����ӵ����ȴ�ʱ��
                .threadsAllowedToBlockForConnectionMultiplier(BLOCKSIZE) // һ��socket���ĵȴ�������
                .writeConcern(WriteConcern.NORMAL).build();
    }

    /**
     * ��ȡ���ϣ���
     *
     * @param collection
     */
    private static DBCollection getCollection(String collection)
    {
        return db.getCollection(collection);
    }

    /**
     * ����
     *
     * @param collection
     * @param map
     */
    public static void insert(String collection, Map<String, Object> map)
    {
        try
        {
            DBObject dbObject = map2Obj(map);
            getCollection(collection).insert(dbObject);
        } catch (MongoException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(String collection, JSONObject json)
    {
        try
        {
            DBObject obj = (DBObject) JSON.parse(json.toJSONString());
            getCollection(collection).insert(obj);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ��������
     *
     * @param collection
     * @param list
     */
    public static void insertBatch(String collection, List<Map<String, Object>> list)
    {
        if (list == null || list.isEmpty())
        {
            return;
        }
        try
        {
            List<DBObject> listDB = new ArrayList<DBObject>();
            for (int i = 0; i < list.size(); i++)
            {
                DBObject dbObject = map2Obj(list.get(i));
                listDB.add(dbObject);
            }
            getCollection(collection).insert(listDB);
        } catch (MongoException e)
        {
            e.printStackTrace();
        }
    }

    public static void insertBatch(String collection, JSONArray jsons)
    {
        if (jsons == null || jsons.isEmpty())
        {
            return;
        }
        try
        {
            List<DBObject> listDB = new ArrayList<DBObject>();
            for (int i = 0; i < jsons.size(); i++)
            {
                DBObject dbObject = (DBObject)JSON.parse(jsons.getJSONObject(i).toJSONString());
                listDB.add(dbObject);
                System.out.println(i);
            }
            getCollection(collection).insert(listDB);
        } catch (MongoException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ɾ��
     *
     * @param collection
     * @param map
     */
    public static void delete(String collection, Map<String, Object> map)
    {
        DBObject obj = map2Obj(map);
        getCollection(collection).remove(obj);
    }

    public static void delete(String collection, JSONObject json)
    {
        DBObject obj = (DBObject)JSON.parse(json.toJSONString());
        getCollection(collection).remove(obj);
    }

    /**
     * ɾ��ȫ��
     *
     * @param collection
     * @param map
     */
    public static void deleteAll(String collection)
    {
        List<DBObject> rs = findAll(collection);
        if (rs != null && !rs.isEmpty())
        {
            for (int i = 0; i < rs.size(); i++)
            {
                getCollection(collection).remove(rs.get(i));
            }
        }
    }

    /**
     * ����ɾ��
     *
     * @param collection
     * @param list
     */
    public static void deleteBatch(String collection, List<Map<String, Object>> list)
    {
        if (list == null || list.isEmpty())
        {
            return;
        }
        for (int i = 0; i < list.size(); i++)
        {
            getCollection(collection).remove(map2Obj(list.get(i)));
        }
    }

    public static void deleteBatch(String collection, JSONArray jsons)
    {
        if (jsons == null || jsons.isEmpty())
        {
            return;
        }
        for (int i = 0; i < jsons.size(); i++)
        {
            getCollection(collection).remove((DBObject)JSON.parse(jsons.getJSONObject(i).toJSONString()));
        }
    }

    /**
     * ����������������
     *
     * @param collection
     * @param map
     */
    public static long getCount(String collection, Map<String, Object> map)
    {
        return getCollection(collection).getCount(map2Obj(map));
    }

    public static long getCount(String collection,JSONObject json)
    {
        return getCollection(collection).getCount((DBObject)JSON.parse(json.toJSONString()));
    }

    /**
     * ���㼯��������
     *
     * @param collection
     * @param map
     */
    public static long getCounts(String collection)
    {
        return getCollection(collection).find().count();
    }

    /**
     * ����
     *
     * @param collection
     * @param setFields
     * @param whereFields
     */
    public static void update(String collection, Map<String, Object> setFields, Map<String, Object> whereFields)
    {
        DBObject obj1 = map2Obj(setFields);
        DBObject obj2 = map2Obj(whereFields);
        getCollection(collection).updateMulti(obj1, obj2);
    }
    public static void update(String collection, JSONObject queryJson,JSONObject updateJson)
    {
        DBObject query =(DBObject)JSON.parse(queryJson.toJSONString());
        DBObject update =(DBObject)JSON.parse(updateJson.toJSONString());
        getCollection(collection).updateMulti(query,update);
    }

    /**
     * ���Ҷ��󣨸�������_id��
     *
     * @param collection
     * @param _id
     */
    public static DBObject findById(String collection, String _id)
    {
        DBObject obj = new BasicDBObject();
        obj.put("_id", massageToObjectId(_id));
        return getCollection(collection).findOne(obj);
    }
    public static JSONObject findOneById(String collection, String _id)
    {
        DBObject obj = new BasicDBObject();
        obj.put("_id", massageToObjectId(_id));
        obj=getCollection(collection).findOne(obj);
        JSONObject json=com.alibaba.fastjson.JSON.parseObject(obj.toString());
        return json;
    }

    /**
     * ���Ҽ������ж���
     *
     * @param collection
     */
    public static List<DBObject> findAll(String collection)
    {
        return getCollection(collection).find().toArray();
    }
    public static JSONArray findAlls(String collection)
    {
        JSONArray jsons=com.alibaba.fastjson.JSON.parseArray(getCollection(collection).find().toArray().toString());
        return jsons;
    }

    /**
     * ���ң�����һ������
     *
     * @param map
     * @param collection
     */
    public static DBObject findOne(String collection, Map<String, Object> map)
    {
        DBCollection coll = getCollection(collection);
        return coll.findOne(map2Obj(map));
    }


    public static JSONObject findOne(String collection, JSONObject json)
    {
        DBObject obj = (DBObject) JSON.parse(json.toJSONString());
        DBCollection col = getCollection(collection);
        obj = col.findOne(obj);
        // System.out.println(obj);
        if(obj!=null){
        	json = com.alibaba.fastjson.JSON.parseObject(obj.toString());
        	 return json;
        }else{
        	return null;
        }


    }


    public static JSONArray findByPage(int pageSize,int page,String collection,JSONObject json){
    	DBCursor limit=getCollection(collection).find((DBObject)JSON.parse(json.toJSONString())).skip((page - 1)*pageSize ).sort(new BasicDBObject("TIME",-1)).limit(pageSize);
    	JSONArray jsonArray=new JSONArray();
    	 while (limit.hasNext()) {
    		   JSONObject object = JSONObject.parseObject(limit.next().toString());
    		   jsonArray.add(object);
         }
    	 //System.out.println(jsonArray);
    	 return jsonArray;
    }
    /**
     * ���ң�����һ��List<DBObject>��
     *
     * @param <DBObject>
     * @param map
     * @param collection
     * @throws Exception
     */
    public static List<DBObject> find(String collection, Map<String, Object> map) throws Exception
    {
        DBCollection coll = getCollection(collection);
        DBCursor c = coll.find(map2Obj(map));
        if (c != null)
            return c.toArray();
        else
            return null;
    }
    public static JSONArray find(String collection, JSONObject json) throws Exception
    {
        List<DBObject> list=new ArrayList<>();
        JSONArray jsons=new JSONArray();
        DBCollection coll = getCollection(collection);
        DBCursor c = coll.find((DBObject)JSON.parse(json.toJSONString())).sort(new BasicDBObject("create_time",-1));
        if (c != null)
        {
//          list= c.toArray();
            jsons=com.alibaba.fastjson.JSON.parseArray(c.toArray().toString());
        }
        return jsons;
    }


    private static DBObject map2Obj(Map<String, Object> map)
    {
        DBObject obj = new BasicDBObject();
        obj.putAll(map);
        // System.out.println(map);
        return obj;
    }

    private static ObjectId massageToObjectId(Object o)
    {
        if (o == null)
            return null;

        if (o instanceof ObjectId)
            return (ObjectId) o;

        if (o instanceof String)
        {
            String s = o.toString();
            if (isValid(s))
                return new ObjectId(s);
        }
        return null;
    }

    private static boolean isValid(String s)
    {
        if (s == null)
            return false;
        if (s.length() < 18)
            return false;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9')
                continue;
            if (c >= 'a' && c <= 'f')
                continue;
            if (c >= 'A' && c <= 'F')
                continue;

            return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
    	//deleteAll("wence");
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("code", "1");
    	map.put("dir_path", "G5A-1");
    	map.put("mcu_ip", "192.168.23.1");
    	map.put("cap_ip", "192.168.23.1");
    	map.put("cap_image_path", "D:\\ftpserver\\192.168.64.85\\2020\\02\\11\\2020.02.11.00.29.46-src-36.2.jpg");
    	map.put("t_ip", "192.168.23.1");
    	map.put("t_image_path", "D:\\ftpserver\\192.168.64.85\\2020\\02\\11\\2020.02.11.00.29.46-dst-36.2.jpg");
    	map.put("t_num", 36.7);
    	map.put("go_time", 20190215095856d);
    	map.put("log_time", 20190215095856d);
    	for(int i =0;i<20;i++) {
    		//insert("wence",map);
    	}
    	List<DBObject> ailist = findAll("wence");
    	System.out.println(ailist.size());
	}
}
