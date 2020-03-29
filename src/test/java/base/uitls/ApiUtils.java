package base.uitls;

import base.pojo.ApiCaseDetail;
import base.pojo.ApiInfo;
import base.pojo.WriteData;
import lombok.Data;

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
    //全局数据池，提供get set方法
    private static List<WriteData> writeDataList = new ArrayList<WriteData>();
    //把每一个用例执行的实际结果，添加到数据池中
    public static void setWriteDataList(WriteData writeData) {
        writeDataList.add(writeData);
    }
    //获取数据池数据
    public static List<WriteData> getWriteDataList() {
        return writeDataList;
    }

    public static Object[][] getData(){
        //测试用例详情列表
        ArrayList<Object> detailList = ExcelUtils.readExcel("/case/testCase04.xlsx", 0, ApiCaseDetail.class);
        //接口基本信息列表
        ArrayList<Object> infoList = ExcelUtils.readExcel("/case/testCase04.xlsx", 1, ApiInfo.class);
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
            /*for (Object object : infoList) {
                ApiInfo apiInfo = (ApiInfo) object;//获取一个apiInfo对象
                if (apiInfo.getApiId().equals(apiId)) {//如果id匹配，将当前的info对象传给对应的一条详情信息
                    apiCaseDetail.setApiInfo(apiInfo);
                    break;//跳出循环，否则
                }
            }*/
            //创建一个一维数组，长度为1
            Object[] itemArray = {apiCaseDetail};//获取每行数据，将每行数据放在一维数组
            datas[i] = itemArray;//将每行数据的一维数组，放在二位数组中
        }
        return datas;
    }
}
