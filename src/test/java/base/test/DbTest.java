package base.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/29 14:46
 */
public class DbTest {

    public static void main(String[] args) {
        List<Map<String, Object>> maps = executeQuery("select id as ID  from loan t where t.id='15';");
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }

    //删、改、加
    public static void execute(String sql) {
        Connection connection = null;
        Statement statement = null;
        InputStream inputStream = DbTest.class.getResourceAsStream("/config/jdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            //加载驱动
            Class.forName(properties.getProperty("driver"));
            //建立连接
            connection = DriverManager.getConnection(url, username, password);
            //创建陈述对象
            statement = connection.createStatement();
            //执行sql
            statement.execute(sql);//删、改、加
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭连接
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查
    public static List<Map<String, Object>> executeQuery(String sql) {
        Connection connection = null;
        Statement statement = null;
        List<Map<String, Object>> requestMapList = null;
        InputStream inputStream = DbTest.class.getResourceAsStream("/config/jdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        //加载驱动
        try {
            Class.forName(properties.getProperty("driver"));
            //建立连接
            connection = DriverManager.getConnection(url, username, password);
            //创建陈述对象
            statement = connection.createStatement();
            //执行sql
            ResultSet resultSet = statement.executeQuery(sql);//查询
            //获取每条记录的元数据：描述数据的数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //动态获取每条记录的列数
            int columnCount = metaData.getColumnCount();
            requestMapList = new ArrayList<Map<String, Object>>();
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
            return requestMapList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭连接
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Map<String, Object>> executeQueryTest(String sql) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://120.78.128.25:3306/futureloan";
        String username = "future";
        String password = "123456";
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //建立连接
        Connection connection = DriverManager.getConnection(url, username, password);
        //创建陈述对象
        Statement statement = connection.createStatement();
        //执行sql
        ResultSet resultSet = statement.executeQuery(sql);//查询
        //获取每条记录的元数据：描述数据的数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        //动态获取每条记录的列数
        int columnCount = metaData.getColumnCount();
        //boolean next = resultSet.next();

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
            //String string = resultSet.getString(1);
            //System.out.println(requestMap);
        }
        //关闭连接
        statement.close();
        connection.close();
        return requestMapList;
    }

    //删、改、加
    public static void executeTest(String sql) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://120.78.128.25:3306/futureloan";
        String username = "future";
        String password = "123456";
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //建立连接
        Connection connection = DriverManager.getConnection(url, username, password);
        //创建陈述对象
        Statement statement = connection.createStatement();
        //执行sql
        statement.execute(sql);//删、改、加
        //关闭连接
        statement.close();
        connection.close();
    }

}
