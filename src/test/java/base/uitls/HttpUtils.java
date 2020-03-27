package base.uitls;


import base.pojo.ApiCaseDetail;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/24 14:09
 */
public class HttpUtils {
    /**
     * @Param: [url, map]
     * @return: java.lang.String
     * @Author: ZS
     * @Date: 2020/3/26
     */
    public static String doGet(String url, Map<String, String> map) {

        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            String username = key;
            String password = map.get(key);
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(username, password);
            parameters.add(basicNameValuePair);
        }
        String s = URLEncodedUtils.format(parameters, "utf-8");
        HttpGet httpGet = new HttpGet(url + "?" + s);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity);// 工具包toString，将响应体转化为字符串
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Param: [url, map]
     * @return: java.lang.String
     * @Author: ZS
     * @Date: 2020/3/26
     */
    public static String doPost(String url, Map<String, String> map) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        //parameters.add(new BasicNameValuePair("username", username));
        //parameters.add(new BasicNameValuePair("password", password));
        //从map中拿值作为参数
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            String username = key;
            String password = map.get(key);
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(username, password);
            parameters.add(basicNameValuePair);
        }
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
            //System.out.println(statusLine.getStatusCode());
            // 3.获取响应体
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);// 工具包toString，将响应体转化为字符串
            return responseString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @Param: requestData:json格式的数据
     * @return:
     * @Author: ZS
     * @Date: 2020/3/26
     */
    public static String doPost(String url, String requestData) {
        String responseEntity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        //设置必须的请求头
        post.setHeader("X-Lemonban-Media-Type", "lemonban.v1");
        StringEntity stringEntity = new StringEntity(requestData, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding("utf-8");
        //设置请求体
        post.setEntity(stringEntity);
        try {
            //发包
            CloseableHttpResponse response = httpClient.execute(post);
            StatusLine statusLine = response.getStatusLine();
            //System.out.println(statusLine.getStatusCode());
            // 3.获取响应体
            HttpEntity entity = response.getEntity();
            responseEntity = EntityUtils.toString(entity);// 工具包toString，将响应体转化为字符串
            return responseEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param apiCaseDetail 参数：用例详情
     * @return
     */
    public static String doPost(ApiCaseDetail apiCaseDetail) {
        return doPost(apiCaseDetail.getApiInfo().getUrl(), apiCaseDetail.getRequestData());
    }

    /**
     * @param apiCaseDetail 参数：用例详情
     * @return
     */
    public static String doGet(ApiCaseDetail apiCaseDetail) {
        return doPost(apiCaseDetail.getApiInfo().getUrl(), apiCaseDetail.getRequestData());
    }

    /**
     * @param url
     * @param requestData json字符串
     * @return
     */
    public static String doGet(String url, String requestData) {
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        //把json参数转化为map队象
        Map<String, Object> jsonMap = JSONObject.parseObject(requestData);
        Set<String> keySet = jsonMap.keySet();
        for (String key : keySet) {
            String username = key;
            String password = jsonMap.get(key).toString();
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(username, password);
            parameters.add(basicNameValuePair);
        }
        String s = URLEncodedUtils.format(parameters, "utf-8");
        HttpGet httpGet = new HttpGet(url + "?" + s);
        //设置必须的请求头
        httpGet.setHeader("X-Lemonban-Media-Type", "lemonban.v1");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity);// 工具包toString，将响应体转化为字符串
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
