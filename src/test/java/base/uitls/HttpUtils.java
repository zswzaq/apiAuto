package base.uitls;

import base.pojo.ApiCaseDetail;
import base.pojo.Header;
import base.pojo.RequestType;
import com.alibaba.fastjson.JSONObject;
import com.lemon.EncryptUtils;
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
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/24 14:09
 */
public class HttpUtils {
    private static Logger log = Logger.getLogger(HttpUtils.class);

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
     * @param apiCaseDetail 参数：用例详情
     * @return
     */
    public static String doPost(ApiCaseDetail apiCaseDetail) {
        String responseEntity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(apiCaseDetail.getApiInfo().getUrl());
        String headers = apiCaseDetail.getApiInfo().getHeaders();//得到所有的headers
        List<Header> headerList = JSONObject.parseArray(headers, Header.class);//将其转化为list

        //设置必须的请求头
        for (Header header : headerList) {
            //把登陆信息中的token提取出来，进行替换，再重新设值进去header里
            String replacedStr = ParamUtils.getReplacedStr(header.getValue());
            post.setHeader(header.getKey(), replacedStr);
        }
        StringEntity stringEntity = new StringEntity(apiCaseDetail.getRequestData(), ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding("utf-8");
        //设置请求体
        post.setEntity(stringEntity);
        try {
            //发包
            CloseableHttpResponse response = httpClient.execute(post);
            StatusLine statusLine = response.getStatusLine();
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
        //post.setHeader("X-Lemonban-Media-Type", "lemonban.v1");
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
    public static String doGet(ApiCaseDetail apiCaseDetail) {
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        //把json参数转化为map对象
        Map<String, Object> jsonMap = JSONObject.parseObject(apiCaseDetail.getRequestData());
        Set<String> keySet = jsonMap.keySet();
        for (String key : keySet) {
            String name = key;
            String value = jsonMap.get(key).toString();
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(name, value);
            parameters.add(basicNameValuePair);
        }
        String s = URLEncodedUtils.format(parameters, "utf-8");
        HttpGet get = new HttpGet(apiCaseDetail.getApiInfo().getUrl() + "?" + s);
        //设置必须的请求头
        String headers = apiCaseDetail.getApiInfo().getHeaders();//得到所有的headers
        List<Header> headerList = JSONObject.parseArray(headers, Header.class);//将其转化为list
        for (Header header : headerList) {
            get.setHeader(header.getKey(), header.getValue());
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity);// 工具包toString，将响应体转化为字符串
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @param requestData json字符串
     * @return
     */
    public static String doGet(String url, String requestData) {
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        //把json参数转化为map对象
        Map<String, Object> jsonMap = JSONObject.parseObject(requestData);
        Set<String> keySet = jsonMap.keySet();
        for (String key : keySet) {//遍历keySet，生成一个名值对
            String name = key;
            String value = jsonMap.get(key).toString();
            //生成，
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(name, value);
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

    //发包
    public static String action(ApiCaseDetail apiCaseDetail) {
        log.info("开始发包请求");
        //请求方法
        String type = apiCaseDetail.getApiInfo().getType();
        //处理请求的数据，替换请求头、请求体
        handleRequestData(apiCaseDetail);
        //请求分发
        String str = null;
        if (RequestType.GET.toString().equalsIgnoreCase(type)) {
            //直接传测试用例
            str = HttpUtils.doGet(apiCaseDetail);
        } else if (RequestType.POST.toString().equalsIgnoreCase(type)) {
            str = HttpUtils.doPost(apiCaseDetail);
        } else if (RequestType.PATCH.toString().equalsIgnoreCase(type)) {
            str = HttpUtils.doPatch(apiCaseDetail);
        }
        return str;
    }

    /**
     * 处理请求的数据，替换请求头、请求体
     * @param apiCaseDetail
     */
    private static void handleRequestData(ApiCaseDetail apiCaseDetail) {
        //需要对请求的数据替换，替换为提取的数据
        String requestData = apiCaseDetail.getRequestData();
        //替换（member-id）
        String replacedSRequestData = ParamUtils.getReplacedStr(requestData);
        //-----获取鉴权的时间戳+token+sign----------
        //首先判断哪些接口需求鉴权，如果不判断，全部鉴权的话，会找不到token报空指针
        String auth = apiCaseDetail.getApiInfo().getAuth();
        if (null != auth && "Y".equals(auth)) {
            //从数据池中拿到对应的token
            String token = ParamUtils.getGlobalData("token").toString();
            long timestamp = System.currentTimeMillis() / 1000;//时间戳
            String tempStr = token.substring(0, 50) + timestamp;//时间戳+token
            String sign = EncryptUtils.rsaEncrypt(tempStr);//加密-->sign
            //将上边首次替换后的请求头转成map
            Map<String, Object> reqStrMap = (Map<String, Object>) JSONObject.parse(replacedSRequestData);
            reqStrMap.put("timestamp", timestamp);//加时间戳
            reqStrMap.put("sign", sign);//加签名
            replacedSRequestData = JSONObject.toJSONString(reqStrMap);//map转为string类型，得到最后的请求体
        }
        //---------------
        //重新设值回去请求体中
        apiCaseDetail.setRequestData(replacedSRequestData);
    }

    public static String doPatch(ApiCaseDetail apiCaseDetail) {
        // TODO
        return null;
    }

    /*public static String doPost(ApiCaseDetail apiCaseDetail) {
        return doPost(apiCaseDetail.getApiInfo().getUrl(), apiCaseDetail.getRequestData());
    }

    public static String doGet(ApiCaseDetail apiCaseDetail) {
        return doPost(apiCaseDetail.getApiInfo().getUrl(), apiCaseDetail.getRequestData());
    }*/

}
