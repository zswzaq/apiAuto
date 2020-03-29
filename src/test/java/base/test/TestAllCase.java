package base.test;

import base.pojo.RegisterData;
import base.uitls.ExcelUtils;
import base.uitls.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/24 14:42
 */
public class TestAllCase {
    public static final String URL = "http://test.lemonban.com/futureloan/mvc/api/member/register";

    /*@DataProvider
    public Object[][] getData() {
        Object[][] datas = {
                new Object[]{"13888888888", ""},
                new Object[]{"", "1244"},
                new Object[]{"13888888888", "123456"}
        };
        return datas;
    }*/

    @DataProvider
    public Object[][] getData() {
        ArrayList<Object> dataList = ExcelUtils.readExcel("/case/register.xlsx", 0, RegisterData.class);
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

        /*for (int i = 0; i < dataList.size(); i++) {
            Object[] itemArray = {dataList.get(i)};
            datas[i] = itemArray;
        }*/
		/*int i=0;//自己控制索引
		for (Object object : dataList) {
			Object[] itemArray = {object};
			datas[i] = itemArray;
			i++;
		}*/

        return datas;

        //        Object[][] datas = {
        //                new Object[]{"13888888888", ""},
        //                new Object[]{"", "1244"},
        //                new Object[]{"13888888888", "123456"}
        //        };
    }

    @Test(dataProvider = "getData")
    public void test1(RegisterData registerData) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mobliephone", registerData.getPhone());
        map.put("pwd", registerData.getPwd());
        map.put("regname", registerData.getRegname());
        String str = HttpUtils.doPost(URL, map);
        System.out.println(str);
    }


    /*@Test(dataProvider = "getData")
    public void test1(String mobliephone,String pwd) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mobliephone", mobliephone);
        map.put("pwd", pwd);
        //map.put("regname", regname);
        String str = HttpUtils.doPost(URL, map);
        System.out.println(str);
    }*/
    public static void main(String[] args) {
        //用jsonPath解析json
        /*String jsonStr = "{\"code\":2,\"msg\":\"账号已存在\",\"data\":null,\"copyright\":\"Copyright 柠檬班 © 2017-2019 湖南省零檬信息技术有限公司 All Rights Reserved\"}\n";
        String jsonPath = "$.code";
        Object parse = Configuration.defaultConfiguration().jsonProvider().parse(jsonStr);
        Object read = JsonPath.read(parse, jsonPath);
        //System.out.println(read);
        //用fastJson的jsonPath解析json
        JSONObject object = JSON.parseObject(jsonStr);
        Object eval = JSONPath.eval(object, "$.msg");
        Object read1 = JSONPath.read(jsonStr, "$.code");
        System.out.println(read1);*/
        writeExcel();
    }
    // 回写数据到excel中
    private static void writeExcel() {
        try {
            InputStream inputStream = TestAllCase.class.getResourceAsStream("/case/testCase.xlsx");
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);//第一个表单
            Row row = sheet.getRow(1);//第2行
            Cell cell = row.getCell(1, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);//第1列
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

