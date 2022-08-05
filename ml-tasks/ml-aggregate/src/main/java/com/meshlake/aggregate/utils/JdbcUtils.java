package com.meshlake.aggregate.utils;

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
    public static ResultSet getSampleData(String tableName)
            throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + "default."
                + tableName);
        statement.close();
        connection.close();
        return rs;
    }

    /**
     * 创建一张表并且插入数据
     */
    public static boolean aggregate(String tableName, String targetTableName)
            throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS" + " default."
                + targetTableName + " AS SELECT name, COUNT(id) AS count FROM default." + tableName +" GROUP BY name");
        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
        return true;
    }
}
