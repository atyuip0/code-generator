package com.zhh.cg.api;

/**
 * []
 * User: zhanghaihe
 * Date: 17-8-28
 * Time: 上午10:35
 */
public class Column {

    private String content;

    private String isNull;

    private Integer length;

    private String type;

    private String actualColumnName;

    private String jdbcType;

    private String fieldName;

    private String isPk;

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
