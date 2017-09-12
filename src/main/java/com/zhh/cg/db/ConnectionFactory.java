package com.zhh.cg.db;

import com.zhh.cg.db.config.JDBCConnectionConfiguration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import static com.zhh.cg.util.StringUtility.stringHasValue;
import static com.zhh.cg.util.messages.Messages.getString;

/**
 * This class assumes that classes are cached elsewhere for performance reasons,
 * but also to make sure that any native libraries are only loaded one time
 * (avoids the dreaded UnsatisfiedLinkError library loaded in another
 * classloader)
 * 
 * @author zhanghaihe
 */
public class ConnectionFactory {

    private static ConnectionFactory instance = new ConnectionFactory();

    public static ConnectionFactory getInstance() {
        return instance;
    }

    /**
	 *  
	 */
    private ConnectionFactory() {
        super();
    }

    public Connection getConnection(JDBCConnectionConfiguration config)
            throws SQLException {
        Driver driver = getDriver(config);

        Properties props = new Properties();

        if (stringHasValue(config.getUserId())) {
            props.setProperty("user", config.getUserId()); //$NON-NLS-1$
        }

        if (stringHasValue(config.getPassword())) {
            props.setProperty("password", config.getPassword()); //$NON-NLS-1$
        }

        Connection conn = driver.connect(config.getConnectionURL(), props);

        if (conn == null) {
            throw new SQLException(getString("RuntimeError.7")); //$NON-NLS-1$
        }

        return conn;
    }

    private Driver getDriver(JDBCConnectionConfiguration connectionInformation) {
        String driverClass = connectionInformation.getDriverClass();
        Driver driver;

        try {
            Class<?> clazz = ObjectFactory.externalClassForName(driverClass);
            driver = (Driver) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(getString("RuntimeError.8"), e); //$NON-NLS-1$
        }

        return driver;
    }
}
