package base.uitls;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/24 14:09
 */
public class HttpUtilsPost {
    //post请求
    public static void main(String[] args) {
        //创建一个httpClient的子类对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个post请求
        HttpPost post = new HttpPost("http://test.lemonban.com/ningmengban/mvc/user/login.json");
        //设置参数
        //json格式
        /*JSONObject jsonParam = new JSONObject();
        jsonParam.put("username", "admin");
        jsonParam.put("password", "123456");
        HttpEntity httpEntity = new StringEntity(jsonParam.toString(), ContentType.APPLICATION_JSON);
        post.setEntity(httpEntity);*/
        //form表单格式1
        //HttpEntity httpEntity = new StringEntity("username=13444444444&password=123456", ContentType.APPLICATION_FORM_URLENCODED);
        //form表单格式2
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("username", "13333334444"));
        parameters.add(new BasicNameValuePair("password", "1121312321"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameters, "utf-8");
            post.setEntity(urlEncodedFormEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置请求体
        try {
            //发包
            CloseableHttpResponse response = httpClient.execute(post);
            StatusLine statusLine = response.getStatusLine();
            System.out.println(statusLine.getStatusCode());
            /*Header[] allHeaders = response.getAllHeaders();
            for (Header allHeader : allHeaders) {
                System.out.println(allHeader);
            }*/
            // 3.获取响应体
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity);// 工具包toString，将响应体转化为字符串
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
