package base.uitls;

import base.pojo.ApiCaseDetail;
import base.pojo.ExceptInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.testng.Assert;

import java.util.List;

/**
 * @author ZS
 * @Description: 断言类
 * @date 2020/3/28 16:03
 */
public class AssertUtils {

    /**
     * @param apiCaseDetail 用例详情，用于找预期结果
     * @param actualResult  实际结果
     * @return
     */
    public static void assertRespEntity(ApiCaseDetail apiCaseDetail, String actualResult) {
        //[{"jsonStr":"$.code","expected":2},{"jsonStr":"$.msg","expected":"账号已存在"}]--->属性确定，抽象出类
        //[{"aaa":"$.code","bbb":2},{"ccc":"$.msg","ddd":"账号已存在"}]--------------------->属性不确定，描述为map

        //期望结果
        String exceptedInfo = apiCaseDetail.getExceptedInfo();
        //将期望结果解析为list
        List<ExceptInfo> exceptInfoList = JSONObject.parseArray(exceptedInfo, ExceptInfo.class);
        //用fastJson的jsonPath解析实际结果的json字符串
        JSONObject jsonObject = JSON.parseObject(actualResult);
        if (exceptInfoList == null) {
            return;
        } else {
            for (ExceptInfo exceptInfo : exceptInfoList) {
                String jsonStr = exceptInfo.getJsonStr();//提取jsonPath方法
                Object expected = exceptInfo.getExpected();//期望的值
                //提取实际结果
                Object actualResultData = JSONPath.eval(jsonObject, jsonStr);
                Assert.assertEquals(actualResultData, expected);
            }
        }
        //return actualResult;
    }

}
