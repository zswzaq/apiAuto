package base.test;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * @author ZS
 * @Description: jdbc工具类
 * @date 2020/3/29 14:46
 */
public class DbUtils {
    static String url;
    static String username;
    static String password;

    //static块，类加载到jvm时候，只运行一次
    static {
        init();
    }

    public static void init() {
        InputStream inputStream = DbUtils.class.getResourceAsStream("/config/jdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            //加载驱动
            Class.forName(properties.getProperty("driver"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //删、改、加
    public static void execute(String sql) throws Exception {
        Connection connection = getConnection();
        //创建陈述对象
        Statement statement = connection.createStatement();
        //执行sql
        statement.execute(sql);//删、改、加
        //关闭连接
        statement.close();
        connection.close();
    }

    //查
    public static List<Map<String, Object>> executeQuery(String sql) throws Exception {
        init();
        //建立连接
        Connection connection = getConnection();
        //创建陈述对象
        Statement statement = connection.createStatement();
        //执行sql
        ResultSet resultSet = statement.executeQuery(sql);//查询
        //获取每条记录的元数据：描述数据的数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        //动态获取每条记录的列数
        int columnCount = metaData.getColumnCount();
        List<Map<String, Object>> requestMapList = new ArrayList<Map<String, Object>>();
        while (resultSet.next()) {
            //数据库每一条数据就是有一个map
            Map<String, Object> requestMap = new HashMap<String, Object>();
            for (int i = 1; i <= columnCount; i++) {
                //列名
                String name = metaData.getColumnLabel(i);
                //列值
                Object value = resultSet.getObject(i);
                requestMap.put(name, value);
            }
            requestMapList.add(requestMap);
        }
        //关闭连接
        statement.close();
        connection.close();
        return requestMapList;

    }



    public static void main(String[] args) throws Exception {
        List<Map<String, Object>> maps = executeQuery("select *  from loan t where t.id='15';");
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }

}
