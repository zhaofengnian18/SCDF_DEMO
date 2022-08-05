package com.meshlake.transform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @ClassName TrinoUtils
 * @Description TODO
 */
public class JdbcUtils {

    private static final String trinoDriver = "io.trino.jdbc.TrinoDriver";

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(JdbcUtils.class);

    /**
     * 加载jdbc的驱动
     */
    static {
        try {
            Class.forName(trinoDriver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得链接
     */
    public static Connection getConnection() throws SQLException {
        String url = String.format("jdbc:trino://emr-master.dev.meshlake.com:8889/hive/default");
        Properties properties = new Properties();
        properties.setProperty("user", "trino");
        return DriverManager.getConnection(url, properties);
    }


    /**
     * 返回表的前n条数据
     */
    public static ResultSet getSampleData(String tableName, int end)
            throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + "default."
                + tableName + " LIMIT 0," + end);
        statement.close();
        connection.close();
        return rs;
    }

    /**
     * 创建一张表并且插入数据
     */
    public static boolean createTable(String tableName)
            throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS" + " default."
                + tableName + " (id int, name varchar)");
        preparedStatement.execute();
        preparedStatement.close();
        PreparedStatement preparedStatement1 =  connection.prepareStatement("INSERT INTO default."+ tableName + " VALUES (1, 'San Jose'), (2, 'San Jose'), (3, 'Oakland')");
        preparedStatement1.execute();
        preparedStatement1.close();
        connection.close();
        return true;
    }
}
