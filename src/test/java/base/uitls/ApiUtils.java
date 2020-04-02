package base.uitls;

import base.pojo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZS
 * @Description: 接口工具类，为数据提供者提供数据
 * @date 2020/3/28 13:43
 */
public class ApiUtils {
    private static Logger log = Logger.getLogger(ApiUtils.class);

    //全局数据池存放写回的实际结果，提供get set方法
    private static List<WriteData> writeDataList = new ArrayList<WriteData>();

    //把每一个用例执行的实际结果，添加到数据池中
    public static void setWriteDataList(WriteData writeData) {
        writeDataList.add(writeData);
    }

    //获取数据池数据
    public static List<WriteData> getWriteDataList() {
        return writeDataList;
    }

    //--------------------
    //全局数据池存放写回的SQL验证实际结果，提供get set方法
    private static List<WriteData> writeSqlData = new ArrayList<WriteData>();

    //把每一个用例执行的sql验证的实际结果，添加到数据池中
    public static void setWriteSqlCheckInfo(WriteData writeData) {
        writeSqlData.add(writeData);
    }

    //获取数据池数据
    public static List<WriteData> getWriteSqlCheckInfoList() {
        return writeSqlData;
    }

    //加SQL验证数据
    public static Object[][] getData() {
        //测试用例详情列表
        ArrayList<Object> detailList = ExcelUtils.readExcel("/case/testCase04.xlsx", 0, ApiCaseDetail.class);
        //接口基本信息列表
        ArrayList<Object> infoList = ExcelUtils.readExcel("/case/testCase04.xlsx", 1, ApiInfo.class);
        //sql验证的数据
        ArrayList<Object> sqlInfoList = ExcelUtils.readExcel("/case/testCase04.xlsx", 2, SqlCheckInfo.class);
        //每个用例对应一条接口基本信息，接口信息相当与测试用例的一个属性
        //优化方案：给一个apiId，就能找到info对象：：：把infoList组装到Map对象里，
        Map<String, ApiInfo> apiInfoMap = new HashMap();
        for (Object obj : infoList) {
            ApiInfo apiInfo = (ApiInfo) obj;
            apiInfoMap.put(apiInfo.getApiId(), apiInfo);
        }

        //准备容器存放sql验证的前置和后置
        Map<String, List<SqlCheckInfo>> sqlCheckMap = new HashMap<String, List<SqlCheckInfo>>();
        //遍历所有的sql验证数据
        for (Object sqlObject : sqlInfoList) {
            //获得一个sqlCHeckInfo
            SqlCheckInfo sqlCheck = (SqlCheckInfo) sqlObject;
            //确定key(以用例ID拼接上type（前置or后置）)
            String key = sqlCheck.getCaseId() + "_" + sqlCheck.getType();
            //根据key获取map的value，也就是前置后置分别存放的List<SqlCheckInfo>>
            List<SqlCheckInfo> sqlCheckInfoList = sqlCheckMap.get(key);
            if (sqlCheckInfoList == null) {//如果没有List，就new，有的话，就添加
                sqlCheckInfoList = new ArrayList<SqlCheckInfo>();
            }
            sqlCheckInfoList.add(sqlCheck);
            sqlCheckMap.put(key, sqlCheckInfoList);

        }

        //创建一个二维数组，长度是测试用例详情数据的行数
        Object[][] datas = new Object[detailList.size()][];
        //遍历excel的每行，放入二维数组
        for (int i = 0; i < detailList.size(); i++) {
            ApiCaseDetail apiCaseDetail = (ApiCaseDetail) detailList.get(i);
            String apiId = apiCaseDetail.getApiId();//拿到详情的apiID
            apiCaseDetail.setApiInfo(apiInfoMap.get(apiId));

            //
            String bfKey = apiCaseDetail.getCaseId() + "_" + "bf";
            String afKey = apiCaseDetail.getCaseId() + "_" + "af";
            //设置前置后置sql验证的列表
            apiCaseDetail.setBeforeCheckList(sqlCheckMap.get(bfKey));
            apiCaseDetail.setAfterCheckList(sqlCheckMap.get(afKey));
            //创建一个一维数组，长度为1
            Object[] itemArray = {apiCaseDetail};//获取每行数据，将每行数据放在一维数组
            datas[i] = itemArray;//将每行数据的一维数组，放在二位数组中
        }
        return datas;
    }

    /**
     * 提取需要参数的数据
     * @param apiCaseDetail
     * @param actualResult
     */
    public static void tqRespData(ApiCaseDetail apiCaseDetail, String actualResult) {
        String tqRespData = apiCaseDetail.getTqRespData();
        List<TqRespData> tqRespDataList = JSONObject.parseArray(tqRespData, TqRespData.class);
        if (tqRespDataList == null) {
            return;
        } else {
            //用jsonPath将实际结果解析
            //Object document = defaultConfiguration().jsonProvider().parse(actualResult);
            //用fastJson的jsonPath解析实际结果的json字符串
            JSONObject jsonObject = JSON.parseObject(actualResult);
            for (TqRespData respData : tqRespDataList) {
                String jsonStr = respData.getJsonStr();//jsonPath
                String paramName = respData.getParamName();//name
                //通过jsonpath提取对应的值
                //提取实际结果(fastjson的jsonpath)
                Object paramValue = JSONPath.eval(jsonObject, jsonStr);
                //Object paramValue = JsonPath.read(document, jsonStr);//jsonpath的方法
                ParamUtils.addGlobalData(paramName, paramValue);
            }
        }
    }
    /**
     * 未加sql验证内容方法
     * @return
     */
    /*public static Object[][] getData(){
        //测试用例详情列表
        ArrayList<Object> detailList = ExcelUtils.readExcel("/case/testCase04.xlsx", 0, ApiCaseDetail.class);
        //接口基本信息列表
        ArrayList<Object> infoList = ExcelUtils.readExcel("/case/testCase04.xlsx", 1, ApiInfo.class);
        //sql验证的数据
        ArrayList<Object> sqlInfoList = ExcelUtils.readExcel("/case/testCase04.xlsx", 2, SqlCheckInfo.class);


        //每个用例对应一条接口基本信息，接口信息相当与测试用例的一个属性
        //创建一个二维数组，长度是测试用例详情数据的行数
        Object[][] datas = new Object[detailList.size()][];
        //优化方案：给一个apiId，就能找到info对象：：：把infoList组装到Map对象里，
        Map<String, ApiInfo> apiInfoMapap = new HashMap();
        for (Object obj : infoList) {
            ApiInfo apiInfo = (ApiInfo) obj;
            apiInfoMapap.put(apiInfo.getApiId(), apiInfo);
        }
        //遍历excel的每行，放入二维数组
        for (int i = 0; i < detailList.size(); i++) {
            ApiCaseDetail apiCaseDetail = (ApiCaseDetail) detailList.get(i);
            String apiId = apiCaseDetail.getApiId();//拿到详情的apiID
            apiCaseDetail.setApiInfo(apiInfoMapap.get(apiId));
            //遍历api基本信息，找对应的apiID（效率较低，优化如上）
            *//*for (Object object : infoList) {
                ApiInfo apiInfo = (ApiInfo) object;//获取一个apiInfo对象
                if (apiInfo.getApiId().equals(apiId)) {//如果id匹配，将当前的info对象传给对应的一条详情信息
                    apiCaseDetail.setApiInfo(apiInfo);
                    break;//跳出循环，否则
                }
            }*//*
            //创建一个一维数组，长度为1
            Object[] itemArray = {apiCaseDetail};//获取每行数据，将每行数据放在一维数组
            datas[i] = itemArray;//将每行数据的一维数组，放在二位数组中
        }
        return datas;
    }*/
}
