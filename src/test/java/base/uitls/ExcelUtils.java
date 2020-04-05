package base.uitls;

import base.pojo.ApiInfo;
import base.pojo.WriteData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZS
 * @Description: excel读写类
 * @date 2020/3/25 12:27
 */
public class ExcelUtils {

    /**
     * 读取excel的方法
     * @param excelPath  Excel相对路径
     * @param sheetIndex 表单索引
     * @param clazz      要读的类的字节码
     * @return
     */
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
                //
                //获取当前excel 的行号
                int rowNo = i + 1;
                String setRowNoMethod = "setRowNo";
                Method method = clazz.getMethod(setRowNoMethod, Integer.class);
                //通过反射设置行号
                method.invoke(object, rowNo);
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
                    //先进行参数替换，再传给它自己
                    cellValue = ParamUtils.getReplacedStr(cellValue);
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

    /**
     * 回写数据到excel中
     * @param sourceExcelPath 文件原路径
     * @param targetExcelPath 文件写入路径
     * @param sheetIndex      sheet表单所引
     * @param writeData       写入的数据
     */
    public static void writeExcel(String sourceExcelPath, String targetExcelPath, int sheetIndex, WriteData writeData) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = ExcelUtils.class.getResourceAsStream(sourceExcelPath);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(sheetIndex);//用例详情表单
            Row row = sheet.getRow(writeData.getRowNo() - 1);//行号
            Cell cell = row.getCell(writeData.getCellNo() - 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);//列
            cell.setCellType(CellType.STRING);
            cell.setCellValue(writeData.getData());
            outputStream = new FileOutputStream(new File(targetExcelPath));
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

    /**
     * @param sourceExcelPath 文件原路径
     * @param targetExcelPath 文件写入路径
     * @param sheetIndex      sheet表单所引
     */
    public static void batchWriteExcel(String sourceExcelPath, String targetExcelPath, int sheetIndex) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        Workbook workbook = null;
        try {
            inputStream = ExcelUtils.class.getResourceAsStream(sourceExcelPath);
            workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(sheetIndex);//用例详情表单
            //获取全局数据池的所有实际结果
            List<WriteData> writeDataList = ApiUtils.getWriteDataList();
            for (WriteData writeData : writeDataList) {
                Row row = sheet.getRow(writeData.getRowNo() - 1);//行号
                Cell cell = row.getCell(writeData.getCellNo() - 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);//列
                cell.setCellType(CellType.STRING);
                cell.setCellValue(writeData.getData());
                outputStream = new FileOutputStream(new File(targetExcelPath));
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 批量回写数据池中的数据到文件中
     * @param sourceExcelPath
     * @param targetExcelPath
     */
    public static void batchWriteExcel(String sourceExcelPath, String targetExcelPath) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        Workbook workbook = null;
        try {
            inputStream = ExcelUtils.class.getResourceAsStream(sourceExcelPath);
            workbook = WorkbookFactory.create(inputStream);
            //-------回写第一个表单---------------
            Sheet sheet0 = workbook.getSheetAt(0);//用例详情表单
            //获取全局数据池的所有实际结果
            List<WriteData> writeDataList = ApiUtils.getWriteDataList();
            for (WriteData writeData : writeDataList) {
                Row row = sheet0.getRow(writeData.getRowNo() - 1);//行号
                Cell cell = row.getCell(writeData.getCellNo() - 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);//列
                cell.setCellType(CellType.STRING);
                cell.setCellValue(writeData.getData());
                outputStream = new FileOutputStream(new File(targetExcelPath));
                workbook.write(outputStream);
            }
            //-------回写第三个表单---------------
            Sheet sheet2 = workbook.getSheetAt(2);//用例详情表单
            //获取全局数据池的所有实际结果
            List<WriteData> writeSqlCheckInfoList = ApiUtils.getWriteSqlCheckInfoList();
            for (WriteData writeData : writeSqlCheckInfoList) {
                Row row = sheet2.getRow(writeData.getRowNo() - 1);//行号
                Cell cell = row.getCell(writeData.getCellNo() - 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);//列
                cell.setCellType(CellType.STRING);
                cell.setCellValue(writeData.getData());
                outputStream = new FileOutputStream(new File(targetExcelPath));
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * @param sourceExcelPath 文件原路径
     * @param targetExcelPath 文件写入路径
     * @param sheetIndex      sheet表单所引
     */
    public static void batchWriteSqlCheck(String sourceExcelPath, String targetExcelPath, int sheetIndex) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        Workbook workbook = null;
        try {
            inputStream = ExcelUtils.class.getResourceAsStream(sourceExcelPath);
            workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(sheetIndex);//用例详情表单
            //获取全局数据池的所有实际结果(SQL验证数据)
            List<WriteData> writeDataList = ApiUtils.getWriteSqlCheckInfoList();
            for (WriteData writeData : writeDataList) {
                Row row = sheet.getRow(writeData.getRowNo() - 1);//行号
                Cell cell = row.getCell(writeData.getCellNo() - 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);//列
                cell.setCellType(CellType.STRING);
                cell.setCellValue(writeData.getData());
                outputStream = new FileOutputStream(new File(targetExcelPath));
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Object> dataList = readExcel("/case/testCase04.xlsx", 1, ApiInfo.class);
        for (Object obj : dataList) {
            System.out.println(obj);
        }
    }
}