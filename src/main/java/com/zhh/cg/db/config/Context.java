package com.zhh.cg.db.config;

import com.zhh.cg.api.IntrospectedColumn;
import com.zhh.cg.api.java.FullyQualifiedJavaType;
import com.zhh.cg.api.java.JavaTypeResolver;
import com.zhh.cg.db.ConnectionFactory;
import com.zhh.cg.db.ObjectFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.zhh.cg.util.JavaBeansUtil.getCamelCaseString;
import static com.zhh.cg.util.StringUtility.stringHasValue;

/**
 * User: zhanghaihe
 * Date: 17-8-28
 * Time: 上午11:01
 */
public class Context {

    /** The jdbc connection configuration. */
    private JDBCConnectionConfiguration jdbcConnectionConfiguration;

    /** The catalog. */
    private String catalog;

    /** The schema. */
    private String schema;

    /** The table name. */
    private String tableName;

    /** The primary key columns. */
    protected List<IntrospectedColumn> primaryKeyColumns = new ArrayList<IntrospectedColumn>();

    protected List<IntrospectedColumn> columns = new ArrayList<IntrospectedColumn>();

    /** The beginning delimiter. */
    private String beginningDelimiter = "\""; //$NON-NLS-1$

    /** The ending delimiter. */
    private String endingDelimiter = "\""; //$NON-NLS-1$

    public Context(Properties properties) {
        parseJdbcConnection(properties);
        catalog = properties.getProperty("catalog"); //$NON-NLS-1$
        schema = properties.getProperty("schema"); //$NON-NLS-1$
        tableName = properties.getProperty("tableName"); //$NON-NLS-1$

    }


    public void introspectTable() throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            getColumns(databaseMetaData);

        } finally {
            closeConnection(connection);
        }
    }

    private List<IntrospectedColumn> getColumns(DatabaseMetaData databaseMetaData) throws SQLException {
        String localCatalog;
        String localSchema;
        String localTableName;

        boolean delimitIdentifiers = false;

        if (delimitIdentifiers) {
            localCatalog = catalog;
            localSchema = schema;
            localTableName = tableName;
        } else if (databaseMetaData.storesLowerCaseIdentifiers()) {
            localCatalog = catalog == null ? null : catalog
                    .toLowerCase();
            localSchema = schema == null ? null : schema
                    .toLowerCase();
            localTableName = tableName == null ? null : tableName.toLowerCase();
        } else if (databaseMetaData.storesUpperCaseIdentifiers()) {
            localCatalog = catalog == null ? null : catalog
                    .toUpperCase();
            localSchema = schema == null ? null : schema
                    .toUpperCase();
            localTableName = tableName == null ? null : tableName.toUpperCase();
        } else {
            localCatalog = catalog;
            localSchema = schema;
            localTableName = tableName;
        }

        if (false) {
            String escapeString = databaseMetaData.getSearchStringEscape();

            StringBuilder sb = new StringBuilder();
            StringTokenizer st;
            if (localSchema != null) {
                st = new StringTokenizer(localSchema, "_%", true); //$NON-NLS-1$
                while (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    if (token.equals("_") //$NON-NLS-1$
                            || token.equals("%")) { //$NON-NLS-1$
                        sb.append(escapeString);
                    }
                    sb.append(token);
                }
                localSchema = sb.toString();
            }

            sb.setLength(0);
            st = new StringTokenizer(localTableName, "_%", true); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if (token.equals("_") //$NON-NLS-1$
                        || token.equals("%")) { //$NON-NLS-1$
                    sb.append(escapeString);
                }
                sb.append(token);
            }
            localTableName = sb.toString();
        }

        ResultSet rs = databaseMetaData.getColumns(localCatalog, localSchema,
                localTableName, null);
        JavaTypeResolver javaTypeResolver = ObjectFactory
                .createJavaTypeResolver(this, null);
        Map<Short, String> keyColumns = calculatePrimaryKey(databaseMetaData);

        while (rs.next()) {
            IntrospectedColumn introspectedColumn = ObjectFactory
                    .createIntrospectedColumn(this);

//            introspectedColumn.setTableAlias(tc.getAlias());
            introspectedColumn.setJdbcType(rs.getInt("DATA_TYPE")); //$NON-NLS-1$
            introspectedColumn.setLength(rs.getInt("COLUMN_SIZE")); //$NON-NLS-1$
            introspectedColumn.setActualColumnName(rs.getString("COLUMN_NAME")); //$NON-NLS-1$
            introspectedColumn
                    .setNullable(rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable); //$NON-NLS-1$
            introspectedColumn.setScale(rs.getInt("DECIMAL_DIGITS")); //$NON-NLS-1$
            introspectedColumn.setRemarks(rs.getString("REMARKS")); //$NON-NLS-1$
            introspectedColumn.setDefaultValue(rs.getString("COLUMN_DEF")); //$NON-NLS-1$
            introspectedColumn.setJavaProperty(
                    getCamelCaseString(introspectedColumn
                            .getActualColumnName(), false));
            FullyQualifiedJavaType fullyQualifiedJavaType = javaTypeResolver
                    .calculateJavaType(introspectedColumn);

            if (fullyQualifiedJavaType != null) {
                introspectedColumn
                        .setFullyQualifiedJavaType(fullyQualifiedJavaType);
                introspectedColumn.setJdbcTypeName(javaTypeResolver
                        .calculateJdbcTypeName(introspectedColumn));
            } else {
                introspectedColumn
                        .setFullyQualifiedJavaType(FullyQualifiedJavaType
                                .getObjectInstance());
                introspectedColumn.setJdbcTypeName("OTHER");
            }
            columns.add(introspectedColumn);
            for (String columnName : keyColumns.values()) {
                if (introspectedColumn.getActualColumnName().equals(columnName)) {
                    primaryKeyColumns.add(introspectedColumn);
                    columns.remove(introspectedColumn);
                    break;
                }
            }
        }

        closeResultSet(rs);
        return columns;
    }

    /**
     * Calculate primary key.
     *
     * @param databaseMetaData
     */
    private Map<Short, String> calculatePrimaryKey(DatabaseMetaData databaseMetaData) {
        ResultSet rs = null;
        // keep primary columns in key sequence order
        Map<Short, String> keyColumns = new TreeMap<Short, String>();
        try {
            rs = databaseMetaData.getPrimaryKeys(
                    catalog, schema
                    ,tableName);
        } catch (SQLException e) {
            closeResultSet(rs);
            return keyColumns;
        }

        try {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME"); //$NON-NLS-1$
                short keySeq = rs.getShort("KEY_SEQ"); //$NON-NLS-1$
                keyColumns.put(keySeq, columnName);
            }
            return keyColumns;
        } catch (SQLException e) {
            // ignore the primary key if there's any error
        } finally {
            closeResultSet(rs);
        }
        return keyColumns;
    }


    private Connection getConnection() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection(
                jdbcConnectionConfiguration);

        return connection;
    }


    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
                ;
            }
        }
    }

    /**
     * Close result set.
     *
     * @param rs
     *            the rs
     */
    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
                ;
            }
        }
    }

    private void parseJdbcConnection(Properties properties){
        jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        String driverClass = properties.getProperty("diver_name"); //$NON-NLS-1$
        String connectionURL = properties.getProperty("url"); //$NON-NLS-1$
        String userId = properties.getProperty("username"); //$NON-NLS-1$
        String password = properties.getProperty("password"); //$NON-NLS-1$

        jdbcConnectionConfiguration.setDriverClass(driverClass);
        jdbcConnectionConfiguration.setConnectionURL(connectionURL);

        if (stringHasValue(userId)) {
            jdbcConnectionConfiguration.setUserId(userId);
        }

        if (stringHasValue(password)) {
            jdbcConnectionConfiguration.setPassword(password);
        }
    }


    /**
     * Gets the beginning delimiter.
     *
     * @return the beginning delimiter
     */
    public String getBeginningDelimiter() {
        return beginningDelimiter;
    }

    /**
     * Gets the ending delimiter.
     *
     * @return the ending delimiter
     */
    public String getEndingDelimiter() {
        return endingDelimiter;
    }


    public JDBCConnectionConfiguration getJdbcConnectionConfiguration() {
        return jdbcConnectionConfiguration;
    }

    public void setJdbcConnectionConfiguration(JDBCConnectionConfiguration jdbcConnectionConfiguration) {
        this.jdbcConnectionConfiguration = jdbcConnectionConfiguration;
    }

    public void setBeginningDelimiter(String beginningDelimiter) {
        this.beginningDelimiter = beginningDelimiter;
    }

    public void setEndingDelimiter(String endingDelimiter) {
        this.endingDelimiter = endingDelimiter;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<IntrospectedColumn> getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    public void setPrimaryKeyColumns(List<IntrospectedColumn> primaryKeyColumns) {
        this.primaryKeyColumns = primaryKeyColumns;
    }

    public List<IntrospectedColumn> getColumns() {
        return columns;
    }
}
