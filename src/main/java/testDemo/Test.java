package testDemo;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: ZS
 * @Date: 2020/3/23
 */
public class Test {

    public static void main(String[] args) throws IOException {
        // Map<> map = new HashMap();
        String str = "{\"name\":\"zs\",\"age\":\"25\",\"sex\":\"男\"}";
        //1. 转成Map（需要强转）parse（）方法
        Map map = (Map) JSONObject.parse(str);
        // System.out.println(map);
        //2. 返回一个对象，parseObject（目标json串，类的字节码对象）Class.class
        User user = JSONObject.parseObject(str, User.class);
        //System.out.println(user);
        String str1 = "[{\"name\":\"zs\",\"age\":\"25\",\"sex\":\"男\"},\n" +
                "{\"name\":\"jack\",\"age\":\"26\",\"sex\":\"女\"}\n" +
                "{\"name\":\"tom\",\"age\":\"24\",\"sex\":\"男\"}]";

	    /*List<Map> mapList = JSONObject.parseArray(str1, Map.class);
	    for (Map map1 : mapList) {
		    System.out.println(map1);
	    }*/
	    /*List<User> users = JSONObject.parseArray(str1, User.class);
	    for (User user1 : users) {
		    System.out.println(user1);
	    }*/

        System.out.println(Math.PI);
        //properties文件
        Properties properties = new Properties();
        InputStream inputStream = Test.class.getResourceAsStream("/user.properties");//输入流对象（相对路径）
        properties.load(inputStream);//加载资源文件
        String name = properties.getProperty("name");
        String age = properties.getProperty("age");
        String sex = properties.getProperty("sex");
        System.out.println(name);
        System.out.println(age);
        System.out.println(sex);

    }


}
