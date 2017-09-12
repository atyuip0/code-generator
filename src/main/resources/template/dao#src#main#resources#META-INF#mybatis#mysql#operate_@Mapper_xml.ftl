<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.a7space.dao.mapper.operate.${entityName}Mapper" >
  <resultMap id="BaseResultMap" type="com.a7space.dao.po.operate.${entityName}PO" >
      <#list columns as po>
      <#if (po.isPk == 'Y')>
      <id column="${po.actualColumnName}" property="${po.fieldName}" jdbcType="${po.jdbcType}" />
      <#else>
      <result column="${po.actualColumnName}" property="${po.fieldName}" jdbcType="${po.jdbcType}" />
      </#if>
      </#list>
  </resultMap>
    <resultMap id="ResultMapWithVO" type="com.a7space.dao.vo.operate.${entityName}VO" extends="BaseResultMap">

    </resultMap>
  <sql id="Base_Column_List" >
    <#list columns as po>
    ${po.actualColumnName}<#if (po_has_next)>,</#if>
    </#list>
  </sql>
  <sql id="Base_VO_Column_List" >
    <#list columns as po>
    a.${po.actualColumnName}<#if (po_has_next)>,</#if>
    </#list>
  </sql>
  <select id="selectByPrimaryKey" resultMap="ResultMapWithVO" parameterType="java.lang.String" >
    select 
    <include refid="Base_VO_Column_List" />
    from ${tableName} a

    where a.${pkA} = ${jing}{${pk},jdbcType=VARCHAR}
  </select>
  <insert id="insert" parameterType="com.a7space.dao.po.operate.${entityName}PO" >
    insert into ${tableName} (
        <#list columns as po>
        ${po.actualColumnName}<#if (po_has_next)>,</#if>
        </#list>
    )
    values (
        <#list columns as po>
        ${jing}{${po.fieldName},jdbcType=${po.jdbcType}}<#if (po_has_next)>,</#if>
        </#list>
    )
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="com.a7space.dao.po.operate.${entityName}PO" >
    update ${tableName}
    <set >
        <#list columns as po>
        <#if (po.isPk != 'Y')>
        <if test="${po.fieldName} != null" >
        ${po.actualColumnName} = ${jing}{${po.fieldName},jdbcType=${po.jdbcType}},
        </if>
        </#if>
        </#list>
    </set>
    where ${pkA} = ${jing}{${pk},jdbcType=VARCHAR}
  </update>

  <select id="selectBySelectiveByPage" resultMap="ResultMapWithVO" parameterType="com.a7space.dao.vo.operate.${entityName}VO" >
    select
    <include refid="Base_VO_Column_List" />
    from ${tableName} a
    <where >
        <#list columns as po>
        <if test="${po.fieldName} != null" >
        and a.${po.actualColumnName} = ${jing}{${po.fieldName},jdbcType=${po.jdbcType}}
        </if>
        </#list>
    </where>
  </select>
</mapper>