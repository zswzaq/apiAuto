package testDemo;

import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/24 0:30
 */
public class ExcelTest {
    public static void main(String[] args) {
        ArrayList<User> users = readExcel("/user.xlsx", 0);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * @Param: excelPath:文件路径；sheetIndex：表单Index
     * @return: ArrayList<User>
     * @Author: ZS
     * @Date: 2020/3/24
     */
    private static ArrayList<User> readExcel(String excelPath, int sheetIndex) {
        //创建一个容器，放置所有用户的数据
        ArrayList<User> users = new ArrayList<User>();

        InputStream inputStream = null;
        try {
            inputStream = ExcelTest.class.getResourceAsStream(excelPath);
            //获取一个工作簿
            Workbook workbook = WorkbookFactory.create(inputStream);
            //获取指定sheet表单
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            /** --------获取第一行数据（属性）开始-------------*/
            //获得一行数据
            Row row = sheet.getRow(0);
        /*//第一行第一列
        Cell cell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);//缺省策略
        //获取值
        String value = cell.getStringCellValue();*/
            short lastCellNum = row.getLastCellNum();//最大列的编号,从1开始
            //创建一个容器，按顺序放所有属性的名称
            String[] nameArray = new String[lastCellNum];

            //遍历所有的列
            for (int i = 0; i < lastCellNum; i++) {
                //获取当前列
                Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);//缺省策略
                //获取值
                String name = cell.getStringCellValue();
                //把当前对应的属性名称放在容易中
                nameArray[i] = name;
            }
            /** --------获取第一行数据（属性）结束--------------*/
            /** --------获取下边所有行数据（值）开始------------*/
            //获取最大行数
            int lastRowNum = sheet.getLastRowNum();//获取最大行数，从1开始
            //遍历每一行数据
            for (int i = 1; i <= lastRowNum; i++) {
                User user = new User();//创建一个对象接收数据
                //当前行
                Row currentRow = sheet.getRow(i);
                //遍历当前行的每一列
                for (int j = 0; j < lastCellNum; j++) {
                    //获取当前列
                    Cell currentRowCell = currentRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    //读取数据前设置单元格类型
                    currentRowCell.setCellType(CellType.STRING);
                    //获取值
                    String currentValue = currentRowCell.getStringCellValue();
                    //获取当前数据的列的属性名
                    String currentName = nameArray[j];
                    //反射设置回对象中
                    String setMethod = "set" + (currentName.charAt(0) + "").toUpperCase() + currentName.substring(1);
                    Method method = User.class.getMethod(setMethod, String.class);
                    method.invoke(user, currentValue);
                }
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

