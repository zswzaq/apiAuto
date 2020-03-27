package base.uitls;

import base.pojo.RegisterData;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/25 12:27
 */
public class ExcelUtils {

    public static ArrayList<Object> readExcel(String excelPath, int sheetIndex, Class clazz) {
        InputStream inputStream = null;
        try {
            // 来一个容器，把所有数据行的数据都保存起来
            ArrayList<Object> dataList = new ArrayList<Object>();
            // 讲文件加载成字节输入流
            inputStream = ExcelUtils.class.getResourceAsStream(excelPath);
            // 获得一个工作簿对象
            Workbook workbook = null;
            workbook = WorkbookFactory.create(inputStream);
            // 获取指定的表单
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            /*** --------------------获取第一行数据（获取所有的属性） */
            // 获得第一行数据
            Row firstRow = sheet.getRow(0);
            // 确定第一行有多少列(最大列的标号)
            int lastCellNum = firstRow.getLastCellNum();
            // 创建一个容器，按顺序保存所有的属性名称
            String[] fieldArray = new String[lastCellNum];
            // 循环遍历获得所有的列
            for (int i = 0; i < lastCellNum; i++) {
                // 获得当前索引对应的列
                Cell cell = firstRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                // 设置cell的类型，全部当成字符串处理
                cell.setCellType(CellType.STRING);
                // 获得该cell的值
                String fieldName = cell.getStringCellValue();
                // 把当前对应的属性名称添加到属性容器中去
                fieldArray[i] = fieldName;
            }
            // 获取最大的行数
            int lastRowNum = sheet.getLastRowNum();
            // 循环数据行每一行
            for (int i = 1; i <= lastRowNum; i++) {
                // 创建一个用户对象来保存数据行的信息
                Object object = clazz.newInstance();
                // 获得当前行
                Row currentRow = sheet.getRow(i);
                // 遍历当前数据行的每一列
                for (int j = 0; j < lastCellNum; j++) {
                    // 获得当前列
                    Cell currentCell = currentRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    // 设置cell的类型
                    currentCell.setCellType(CellType.STRING);
                    // 得到当前列的值
                    String cellValue = currentCell.getStringCellValue();
                    // 获得当前数据列对应的属性名
                    String fieldName = fieldArray[j];
                    // 获取属性名对应的setter方法：
                    String methodName = "set" + fieldName;
                    // 获得方法对应的反射对象
                    Method setMethod = clazz.getMethod(methodName, String.class);
                    // 反射调用
                    setMethod.invoke(object, cellValue);
                }
                // 把这个用户保存到容器中间去
                dataList.add(object);
            }
            // 返回所有的数据
            return dataList;
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

    public static void main(String[] args) {
        ArrayList<Object> dataList = readExcel("/case/register.xlsx", 0, RegisterData.class);
        for (Object obj : dataList) {
            System.out.println(obj);
        }
    }
}