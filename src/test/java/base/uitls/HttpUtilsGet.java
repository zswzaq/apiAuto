package base.uitls;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/24 14:09
 */
public class HttpUtilsGet {
    //get请求
    public static void main(String[] args) {
        //创建一个httpClient的子类对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个get请求
        HttpGet get = new HttpGet("https://www.baidu.com/");
        try {
            //发包
            CloseableHttpResponse response = httpClient.execute(get);
            Header[] allHeaders = response.getAllHeaders();//请求头
            /*for (Header allHeader : allHeaders) {
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
