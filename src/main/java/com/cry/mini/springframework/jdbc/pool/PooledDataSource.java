package com.cry.mini.springframework.jdbc.pool;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * <p>
 * 功能描述: 数据库连接池
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/21 18:09
 */
public class PooledDataSource implements DataSource {
    private List<PooledConnection> connections = null;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int initialSize = 2;
    private Properties connectionProperties;


    public PooledDataSource() {
    }

    private void initPool() {
        // 线程同步的list: Collections.synchronizedList()
        this.connections = new ArrayList<>(initialSize);
        try {
            System.out.println("ConnectionPool begin init!");
            for (int i = 0; i < initialSize; i++) {
                Connection connect = DriverManager.getConnection(url, username, password);
                PooledConnection pooledConnection = new PooledConnection(connect, false);
                this.connections.add(pooledConnection);
                System.out.println("=========add connection to pool , now pool size is " + connections.size() + "===========");
            }
            System.out.println("ConnectionPool end init!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnectionFromDriver(getUsername(), getPassword());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnectionFromDriver(username, password);
    }

    //将参数组织成Properties结构，然后拿到实际的数据库连接
    protected Connection getConnectionFromDriver(String username, String password) throws SQLException {
        Properties mergedProps = new Properties();
        Properties connProps = getConnectionProperties();
        if (connProps != null) {
            mergedProps.putAll(connProps);
        }
        if (username != null) {
            mergedProps.setProperty("user", username);
        }
        if (password != null) {
            mergedProps.setProperty("password", password);
        }
        if (this.connections == null) {
            initPool();
        }
        // 获取pool中未被使用的connection
        PooledConnection pooledConnection = getAvailableConnection();
        // 打印当前已使用的connection数量和总数
        System.out.println("当前配置的jdbc连接池大小: " + connections.size());
        System.out.println("已使用的jdbc连接数量: " + connections.stream().filter(PooledConnection::isActive).count());
        // 满了
        if (pooledConnection == null) {
            pooledConnection = getAvailableConnection();
            if (pooledConnection == null) {
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                    throw new RuntimeException("数据库连接池已满");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return pooledConnection;
    }

    private PooledConnection getAvailableConnection() throws SQLException {
        for (PooledConnection pooledConnection : this.connections) {
            // 遍历所有active=false的connection
            if (!pooledConnection.isActive()) {
                pooledConnection.setActive(true);
                return pooledConnection;
            }
        }

        return null;
    }

    protected Connection getConnectionFromDriverManager(String url, Properties props) throws SQLException {
        return DriverManager.getConnection(url, props);
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        try {
            Class.forName(this.driverClassName);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Could not load JDBC driver class [" + driverClassName + "]", ex);
        }

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter arg0) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int arg0) throws SQLException {

    }

    @Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        return null;
    }

    @Override
    public String toString() {
        return "PooledDataSource{" +
                "connections=" + connections +
                ", driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", initialSize=" + initialSize +
                ", connectionProperties=" + connectionProperties +
                '}';
    }
}
