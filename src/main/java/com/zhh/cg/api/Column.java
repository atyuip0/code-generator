package com.zhh.cg.api;

/**
 * []
 * User: zhanghaihe
 * Date: 17-8-28
 * Time: 上午10:35
 */
public class Column {

    //字段注释
    private String content;

    //是否为空（'Y','N'）
    private String isNull;

    //字段长度
    private Integer length;

    //字段Java类型
    private String type;

    //字段数据库名
    private String actualColumnName;

    // 字段数据库类型
    private String jdbcType;

    // 字段
    private String fieldName;

    //是否主键
    private String isPk;

    //默认值
    private String defaultValue;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String aNull) {
        isNull = aNull;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getIsPk() {
        return isPk;
    }

    public void setIsPk(String pk) {
        isPk = pk;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getActualColumnName() {
        return actualColumnName;
    }

    public void setActualColumnName(String actualColumnName) {
        this.actualColumnName = actualColumnName;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }
}
