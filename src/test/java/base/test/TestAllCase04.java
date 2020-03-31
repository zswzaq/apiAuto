package base.test;

import base.pojo.ApiCaseDetail;
import base.pojo.WriteData;
import base.uitls.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ZS
 * @Description: 测试类
 * @date 2020/3/28 23:22
 */
public class TestAllCase04 {

    @BeforeSuite
    public String beforeSuite() {
        //数据初始化，得到随机的手机号
        String tel = RdPersonUtils.getTel();
        ParamUtils.addGlobalData("mobile_phone", tel);
        ParamUtils.addGlobalData("pwd", "12345678");
        return tel;
    }

    @DataProvider
    public Object[][] getData() {
        return ApiUtils.getData();
    }

    @Test(dataProvider = "getData")
    public void test0331(ApiCaseDetail apiCaseDetail) {
        //前置验证
        SqlCheckUtils.beforeCheck(apiCaseDetail);
        //发包，得到实际响应
        String actualResult = HttpUtils.action(apiCaseDetail);

        //实际结果数据
        WriteData writeData = new WriteData(apiCaseDetail.getRowNo(), 5, actualResult);
        //搜集实际结果，回写数据
        ApiUtils.setWriteDataList(writeData);
        //提取需要参数的数据
        ApiUtils.tqRespData(apiCaseDetail,actualResult);
        //后置验证
        SqlCheckUtils.afterCheck(apiCaseDetail);
        //断言实际结果与预计结果
        AssertUtils.assertRespEntity(apiCaseDetail, actualResult);
        System.out.println(actualResult);
    }

    /*@Test(dataProvider = "getData")
    public void test0329(ApiCaseDetail apiCaseDetail) {
        String actualResult = HttpUtils.action(apiCaseDetail);
        //前置验证
        SqlCheckUtils.beforeCheck(apiCaseDetail);
        //实际结果数据
        WriteData writeData = new WriteData(apiCaseDetail.getRowNo(), 5, actualResult);
        //搜集实际结果
        ApiUtils.setWriteDataList(writeData);
        //前置验证
        SqlCheckUtils.afterCheck(apiCaseDetail);
        //断言实际结果与预计结果
        AssertUtils.assertRespEntity(apiCaseDetail, actualResult);
        System.out.println(actualResult);
    }*/

    /*@Test(dataProvider = "getData")
    public void test0328(ApiCaseDetail apiCaseDetail) {
        //String str = HttpUtils.action(apiCaseDetail);
        String actualResult = HttpUtils.action(apiCaseDetail);
        //实际结果数据
        //行号：getRowNo ，列号：5，写回的内容
        WriteData writeData = new WriteData(apiCaseDetail.getRowNo(), 5, actualResult);
        //搜集实际结果
        ApiUtils.setWriteDataList(writeData);

        //写回excel    原路径，目标路径，表单索引、行索引、列索引----------
        // ----（这样写回覆盖，只留下最后一个case的实际结果，且浪费资源）----》优化方案::先搜集数据，再在afterSuite后写入
        //ExcelUtils.writeExcel("/case/testCase04.xlsx", "D:\\testStudy\\a.xlsx", 0, writeData);
        //断言实际结果与预计结果
        AssertUtils.assertRespEntity(apiCaseDetail, actualResult);
        System.out.println(actualResult);

    }*/

    //执行完所有用例后，再写入所有的实际结果的数据
    @AfterSuite
    public void afterSuite() {
        ExcelUtils.batchWriteExcel("/case/testCase04.xlsx", "D:\\testStudy\\test.xlsx", 0);
        ExcelUtils.batchWriteSqlCheck("/case/testCase04.xlsx", "D:\\testStudy\\test.xlsx", 2);

    }

    public static void main(String[] args) {
        //回写测试
        try {
            InputStream inputStream = TestAllCase.class.getResourceAsStream("/case/testCase.xlsx");
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);//第一个表单
            Row row = sheet.getRow(1);//第2行
            Cell cell = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//第1列
            cell.setCellType(CellType.STRING);
            cell.setCellValue("你好啊");
            FileOutputStream outputStream = new FileOutputStream("D:\\testStudy\\a.xlsx");
            workbook.write(outputStream);
            if (outputStream != null) {
                outputStream.close();
            }
            if (workbook != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}

