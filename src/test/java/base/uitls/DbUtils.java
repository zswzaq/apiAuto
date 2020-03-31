package base.uitls;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

/**
 * @author ZS
 * @Description: jdbc工具类
 * @date 2020/3/29 14:46
 */
public class DbUtils {

    private static String url;
    private static String user;
    private static String password;

    /**
     * static块只会在当前类加载到jvm时运行一遍
     */
    static {
        init();
    }

    public static void init() {
        try {
            // 1:从属性文件加载出数据库连接信息
            Properties properties = new Properties();
            InputStream inStream = DbUtils.class.getResourceAsStream("/config/jdbc.properties");
            properties.load(inStream);
            url = properties.getProperty("url");
            user = properties.getProperty("username");
            password = properties.getProperty("password");

            // 2:装载MySQL驱动程序Driver，安装驱动管理器DriverManager
            String driverName = properties.getProperty("driver");
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化方法：从属性文件加载出数据库连接信息、把驱动加载到jvm
     */


    // 增删改
    public static void execute(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            // 4:创建陈述对象
            stmt = conn.createStatement();
            // 5:执行SQL语句
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt);
        }
    }

    //查
    public static List<LinkedHashMap<String, Object>> executeQuery(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            // 保存所有记录的list集合
            List<LinkedHashMap<String, Object>> allResultSet = new ArrayList<LinkedHashMap<String, Object>>();
            // 2:建立连接
            conn = getConnection();
            // 4:创建陈述对象
            stmt = conn.createStatement();
            // 5:执行SQL语句
            resultSet = stmt.executeQuery(sql);
            // 获得结果节的元数据（描述数据的数据）
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 获得列数 10??
            int columnCount = metaData.getColumnCount();
            // 1：结果可能没有，1条或者多条，每条有1个字段和多个字段
            while (resultSet.next()) {
                // 数据库查询出来的每一条记录都是一个map
                LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
                // 这一行有很多列表，现在要把列名作为key，对应记录的的列值作为map的值
                // 动态获得记录的列数
                // 循环所有列
                for (int i = 1; i <= columnCount; i++) {
                    // 列名
                    String columnName = metaData.getColumnLabel(i);
                    // 列值值
                    Object columnValue = resultSet.getObject(i);
                    // put到对应结果map中
                    resultMap.put(columnName, columnValue);
                }
                // 添加到list中去
                allResultSet.add(resultMap);
            }
            return allResultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, resultSet);
        }
        return null;
    }

    //关连接池
    private static void close(Connection conn, Statement stmt, ResultSet resultSet) {
        // 6:关闭资源（关闭陈述对象，关闭连接）
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(conn, stmt);
    }

    //关连接池
    private static void close(Connection conn, Statement stmt) {
        // 6:关闭资源（关闭陈述对象，关闭连接）
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        List<LinkedHashMap<String, Object>> LinkedHashMaps = executeQuery("select *  from loan t where t.id='15';");
        for (LinkedHashMap<String, Object> LinkedHashMap : LinkedHashMaps) {
            System.out.println(LinkedHashMap);
        }
    }

}
