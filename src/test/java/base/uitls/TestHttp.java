package base.uitls;

import java.util.HashMap;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/25 0:40
 */
public class TestHttp {
    public static void main(String[] args) {
        /*String url = "http://test.lemonban.com/ningmengban/mvc/user/login.json";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", "12132423");
        map.put("password", "123123");
        String str = HttpUtils.doPost(url, map);
        System.out.println(str);*/

        /*String url = "http://test.lemonban.com/ningmengban/mvc/user/register";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", "13888888888");
        map.put("password", "123123");
        map.put("code", "23222");
        String str = HttpUtils.doGet(url, map);
        System.out.println(str);*/

        String url = "http://test.lemonban.com/futureloan/mvc/api/member/register";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mobliephone", "13888888888");
        map.put("pwd", "123123");
        String str = HttpUtils.doPost(url, map);
        System.out.println(str);
    }
}
