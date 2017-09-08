package com.zhh.cg.api;

import java.util.List;

/**
 * []
 * User: zhanghaihe
 * Date: 17-8-28
 * Time: 上午10:30
 */
public class EntityMap {

    //基础业务包路径
    private String bussiPackage;

    //业务包名
    private String entityPackage;

    private String entityName;

    private String tableName;

    private String ftl_description;

    private String ftl_create_time;

    private List<Column> columns;

    private String pk;

    private String pkA;

    public String getBussiPackage() {
        return bussiPackage;
    }

    public void setBussiPackage(String bussiPackage) {
        this.bussiPackage = bussiPackage;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getFtl_description() {
        return ftl_description;
    }

    public void setFtl_description(String ftl_description) {
        this.ftl_description = ftl_description;
    }

    public String getFtl_create_time() {
        return ftl_create_time;
    }

    public void setFtl_create_time(String ftl_create_time) {
        this.ftl_create_time = ftl_create_time;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPkA() {
        return pkA;
    }

    public void setPkA(String pkA) {
        this.pkA = pkA;
    }
}
