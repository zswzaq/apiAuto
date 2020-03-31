package base.uitls;

import base.pojo.ApiCaseDetail;
import base.pojo.SqlCheckInfo;
import base.pojo.WriteData;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author ZS
 * @Description: sql验证工具类
 * @date 2020/3/29 17:29
 */
public class SqlCheckUtils {

    public static void beforeCheck(ApiCaseDetail apiCaseDetail) {
        check(apiCaseDetail, 1);
    }

    public static void afterCheck(ApiCaseDetail apiCaseDetail) {
        check(apiCaseDetail, 2);

    }

    private static void check(ApiCaseDetail apiCaseDetail, int type) {
        List<SqlCheckInfo> checkList = null;
        if (type == 1) {

            checkList = apiCaseDetail.getBeforeCheckList();
        } else if (type == 2) {
            checkList = apiCaseDetail.getAfterCheckList();
        }

        if (checkList == null) {
            return;
        } else {
            for (SqlCheckInfo sqlCheckInfo : checkList) {
                String expect = sqlCheckInfo.getExpect();//期望
                List<LinkedHashMap<String, Object>> resultList = DbUtils.executeQuery(sqlCheckInfo.getSql());
                String actual = JSONObject.toJSONString(resultList);
                //写回sql执行结果
                ApiUtils.setWriteSqlCheckInfo(new WriteData(sqlCheckInfo.getRowNo(),6,actual));
                if (expect.equals(actual)) {
                    ApiUtils.setWriteSqlCheckInfo(new WriteData(sqlCheckInfo.getRowNo(), 7, "pass"));
                } else {
                    ApiUtils.setWriteSqlCheckInfo(new WriteData(sqlCheckInfo.getRowNo(), 7, "fail"));

                }
                if (type == 1) {
                    System.out.println("前置期望：：" + expect);
                    System.out.println("前置实际：：" + actual);
                } else {
                    System.out.println("后置期望：：" + expect);
                    System.out.println("后置实际：：" + actual);
                }
            }
        }
    }
    /*public static void beforeCheck(ApiCaseDetail apiCaseDetail) {
        List<SqlCheckInfo> checkList = apiCaseDetail.getBeforeCheckList();
        if (checkList == null) {
            return;
        } else {
            for (SqlCheckInfo sqlCheckInfo : checkList) {
                String expect = sqlCheckInfo.getExpect();//期望
                List<LinkedHashMap<String, Object>> resultList = DbUtils.executeQuery(sqlCheckInfo.getSql());
                String actual = JSONObject.toJSONString(resultList);//实际
                System.out.println("前置期望：：" + expect);
                System.out.println("前置实际：：" + actual);
            }
        }
    }

    public static void afterCheck(ApiCaseDetail apiCaseDetail) {
        List<SqlCheckInfo> checkList = apiCaseDetail.getAfterCheckList();
        if (checkList == null) {
            return;
        } else {
            for (SqlCheckInfo sqlCheckInfo : checkList) {
                String expect = sqlCheckInfo.getExpect();//期望
                List<LinkedHashMap<String, Object>> resultList = DbUtils.executeQuery(sqlCheckInfo.getSql());
                String actual = JSONObject.toJSONString(resultList);
                System.out.println("后置期望：：" + expect);
                System.out.println("后置实际：：" + actual);
            }
        }
    }*/

}
