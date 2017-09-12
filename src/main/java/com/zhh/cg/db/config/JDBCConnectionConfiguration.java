package com.zhh.cg.db.config;

/**
 * 
 * @author zhanghaihe
 */
public class JDBCConnectionConfiguration{

    private String driverClass;

    private String connectionURL;

    private String userId;

    private String password;

    public JDBCConnectionConfiguration() {
        super();
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

}
