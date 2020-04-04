package base.test;

import base.pojo.ApiCaseDetail;
import base.pojo.ApiInfo;
import base.uitls.ApiUtils;
import base.uitls.AssertUtils;
import base.uitls.ExcelUtils;
import base.uitls.HttpUtils;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;

/**
 * @author ZS
 * @Description: 测试类
 * @date 2020/3/26 23:22
 */
public class TestAllCase02 {

    @DataProvider
    public Object[][] getData() {
        return ApiUtils.getData();
    }

    //@Test(dataProvider = "getData")
    public void test0328(ApiCaseDetail apiCaseDetail) {
        //String str = HttpUtils.action(apiCaseDetail);
        //断言实际结果与预计结果
        String actualResult = HttpUtils.action(apiCaseDetail);
        AssertUtils.assertRespEntity(apiCaseDetail,actualResult);
        System.out.println(actualResult);

    }

    /*@Test(dataProvider = "getData")
    public void test0327(ApiCaseDetail apiCaseDetail) {
        //直接传测试用例
        String str = HttpUtils.doPost(apiCaseDetail);
        System.out.println(str);
    }*/

    /*@Test(dataProvider = "getData")
    public void test1(ApiCaseDetail apiCaseDetail) {
        String url = apiCaseDetail.getApiInfo().getUrl();
        String requestData = apiCaseDetail.getRequestData();
        String str = HttpUtils.doPost(url, requestData);
        System.out.println(str);
    }*/

    public static void main(String[] args) {
        //详情
        /*ArrayList<Object> detailList = ExcelUtils.readExcel("/case/testCase02.xlsx", 0, ApiCaseDetail.class);
        for (Object object : detailList) {
            System.out.println(object);
        }*/
        //api信息
        ArrayList<Object> infoList = ExcelUtils.readExcel("/case/testCase03.xlsx", 1, ApiInfo.class);
        for (Object o : infoList) {
            System.out.println(o);
        }


        String a ="\"aaa\":2,\"bbb\":\"";
        String replace = a.replace("\"", "");
        System.out.println(replace);
    }
}

