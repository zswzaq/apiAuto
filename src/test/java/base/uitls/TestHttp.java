package base.uitls;


import org.apache.log4j.Logger;

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
        /*String url = "http://test.lemonban.com/futureloan/mvc/api/member/register";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mobliephone", "13888888888");
        map.put("pwd", "123123");
        String str = HttpUtils.doPost(url, map);
        System.out.println(str);*/
        /*String reqStr ="{\"member_id\":1,\"amount\":0}";
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJtZW1iZXJfaWQiOjgwMTE2NTcsImV4cCI6MTU4NTg0MTE3NH0.elfa3gkOGSXWXBe3NnzGJ1ItXZrxNqkFwors5ZHym21MGtnD0FytElVnIhxtbACrTZlbOHbCndDUq-ti2x6tvg";
        //时间戳
        long timestamp = System.currentTimeMillis() / 1000;
        String sign = token.substring(0,50)+timestamp;
        String rsaEncrypt = EncryptUtils.rsaEncrypt(sign);
        System.out.println(sign);
        System.out.println(rsaEncrypt);

        Map<String,Object> map = (Map<String, Object>) JSONObject.parse(reqStr);

        map.put("timestamp",timestamp);
        map.put("sign",sign);
        String jsonString = JSONObject.toJSONString(map);
        System.out.println(map);
        System.out.println(jsonString);*/


    }
}
