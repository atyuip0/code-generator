package com.zhh.cg.api;

import com.zhh.cg.db.config.Context;
import com.zhh.cg.util.JavaBeansUtil;
import com.zhh.cg.util.PropertiesUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * []
 * User: zhanghaihe
 * Date: 17-8-28
 * Time: 上午10:23
 */
public class CodeGenerator {
    private Properties properties;

    public CodeGenerator(){
        this.properties = PropertiesUtils.loadProps("/cg-config.properties");
    }

    public void generate(){
        try {
            EntityMap entityMap = introspectEntityMap();
            String encoding = properties.getProperty("encoding");
            File tmpDir =  new File(CodeGenerator.class.getResource("/template").getPath());
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setDirectoryForTemplateLoading(tmpDir);
            cfg.setDefaultEncoding(encoding);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            String outputdir = properties.getProperty("outputdir");
            File[] files = tmpDir.listFiles();
            for(File file:files){
                String tempName = file.getName();
                Template temp = cfg.getTemplate(tempName);
                String outFileNmae = getGenerateFileNmae(tempName,entityMap);
                File outfile = new File(outputdir+File.separator+outFileNmae);
                if (!outfile.getParentFile().exists()) {// 判断目标文件所在的目录是否存在
                    // 如果目标文件所在的文件夹不存在，则创建父文件夹
                    outfile.getParentFile().mkdirs();
                }
                outfile.createNewFile();
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outfile),encoding);
                temp.process(entityMap, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private String getGenerateFileNmae(String str,EntityMap entityMap){
        String outFileNmae = str;
        outFileNmae = outFileNmae.replace("[entityPackage]",entityMap.getEntityPackage());
        outFileNmae = outFileNmae.replace("[pagePackage]",entityMap.getPagePackage());
        outFileNmae = outFileNmae.replace("@",entityMap.getEntityName());
        String[] ss1 = outFileNmae.split("\\.");
        String[] ss2 = ss1[0].split("_");
        String suffix = "."+ss2[2];
        String fN = ss2[1]+suffix;
        StringBuffer fP = new StringBuffer();
        String[] ss3 = ss2[0].split("#");
        for(String s:ss3) {
            fP.append(s).append(File.separator);
        }
        return fP+fN;
    }

    private EntityMap introspectEntityMap(){
        EntityMap entityMap = new EntityMap();
        entityMap.setFtl_create_time(LocalDateTime.now().toString());
        entityMap.setEntityPackage(properties.getProperty("entity_package"));
        entityMap.setPagePackage(properties.getProperty("page_package"));
        entityMap.setFtl_description(properties.getProperty("bussi_description"));
        entityMap.setAuthor(properties.getProperty("author"));
        List<Column> columns = new ArrayList<Column>();
        entityMap.setColumns(columns);
        try {
            Context context = new Context(properties);
            context.introspectTable();
            entityMap.setTableName(context.getTableName());
            entityMap.setEntityName(JavaBeansUtil.getCamelCaseString(context.getTableName(),true).substring(1));
            for(IntrospectedColumn introspectedColumn : context.getPrimaryKeyColumns()){
                entityMap.setPk(introspectedColumn.getJavaProperty());
                entityMap.setPkA(introspectedColumn.getActualColumnName());
                Column column = new Column();
                column.setContent(introspectedColumn.getRemarks());
                column.setIsNull(introspectedColumn.isNullable() ? "Y" : "N");
                column.setLength(introspectedColumn.getLength());
                column.setActualColumnName(introspectedColumn.getActualColumnName());
                column.setJdbcType(introspectedColumn.getJdbcTypeName());
                column.setFieldName(introspectedColumn.getJavaProperty());
                column.setDefaultValue(introspectedColumn.getDefaultValue());
                column.setType(introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName());
                column.setIsPk("Y");
                columns.add(column);
            }
            for(IntrospectedColumn introspectedColumn : context.getColumns()){
                Column column = new Column();
                column.setContent(introspectedColumn.getRemarks());
                column.setIsNull(introspectedColumn.isNullable() ? "Y" : "N");
                column.setLength(introspectedColumn.getLength());
                column.setActualColumnName(introspectedColumn.getActualColumnName());
                column.setJdbcType(introspectedColumn.getJdbcTypeName());
                column.setFieldName(introspectedColumn.getJavaProperty());
                column.setDefaultValue(introspectedColumn.getDefaultValue());
                column.setType(introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName());
                column.setIsPk("N");
                columns.add(column);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entityMap;
    }


}
