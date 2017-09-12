package com.zhh.cg.api;

import java.util.List;

/**
 * []
 * User: zhanghaihe
 * Date: 17-8-28
 * Time: 上午10:30
 */
public class EntityMap {

    //业务包
    private String entityPackage;

    //作者
    private String author;

    //业务名
    private String entityName;

    //表名
    private String tableName;

    //业务页面包
    private String pagePackage;

    //业务描述
    private String ftl_description;

    //创建时间
    private String ftl_create_time;

    //字段集合
    private List<Column> columns;

    //主键
    private String pk;

    //主键的数据库字段名
    private String pkA;

    //特殊符号 ‘#’用来替换
    private String jing = "#";

    //特殊符号 ‘@’用来替换
    private String at = "@";

    //特殊符号 ‘$’用来替换
    private String dollar = "$";

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

    public String getPagePackage() {
        return pagePackage;
    }

    public void setPagePackage(String pagePackage) {
        this.pagePackage = pagePackage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getJing() {
        return jing;
    }

    public String getAt() {
        return at;
    }

    public String getDollar() {
        return dollar;
    }
}
