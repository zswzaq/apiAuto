package base.test;

import base.pojo.ApiCaseDemo;
import base.uitls.ExcelUtils;
import base.uitls.HttpUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/26 23:22
 */
public class TestAllCase01 {

    @DataProvider
    public Object[][] getData() {
        ArrayList<Object> dataList = ExcelUtils.readExcel("/case/testCase01.xlsx", 0, ApiCaseDemo.class);
        //创建一个二维数组，长度是excel数据的行数
        Object[][] datas = new Object[dataList.size()][];
        //遍历excel的每行，放入二维数组
        for (int i = 0; i < dataList.size(); i++) {
            //RegisterData registerData = (RegisterData) dataList.get(i);//获取每行数据
            //创建一个一维数组，长度为1
            Object[] itemData = new Object[1];
            itemData[0] = dataList.get(i);//获取每行数据，将每行数据放在一维数组
            datas[i] = itemData;//将每行数据的一维数组，放在二位数组中
        }
        return datas;
    }

    @Test(dataProvider = "getData")
    public void test1(ApiCaseDemo apiCase) {
        String url = apiCase.getUrl();
        String requestData=apiCase.getRequestData();
        String str = HttpUtils.doPost(url, requestData);
        System.out.println(str);
    }

    /*@Test
    public void test() {
        String url = "http://120.78.128.25:8766/futureloan/member/register";
        String requestData ="{\"mobile_phone\":\"13788889999\",\"pwd\":\"12345678\"}";
        String s = HttpUtils.doPost(url, requestData);
        System.out.println(s);
    }
    @Test
    public void testGet() {
        String url = "http://120.78.128.25:8766/futureloan/member/loan";
        String requestData ="{\"pageIndex\":\"2\",\"pageSize\":\"2\"}";
        String s = HttpUtils.doGet(url, requestData);
        System.out.println(s);
    }*/

}

