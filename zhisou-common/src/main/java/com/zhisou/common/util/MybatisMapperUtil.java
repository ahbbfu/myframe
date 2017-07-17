package com.zhisou.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
 
/**
 *  自动生成MyBatis的实体类、实体映射XML文件、Mapper
 *
 * @author   WYS
 * @date     2014-11-8
 * @version  v1.0
 */
public class MybatisMapperUtil {
 
    /**
     **********************************使用前必读*******************
     **
     ** 使用前请配置好jdbc.properties，其他无须改动。
     **
     ***********************************************************
     */
 
    private final String type_char = "char";
 
    private final String type_date = "date";
 
    private final String type_timestamp = "timestamp";
 
    private final String type_int = "int";
 
    private final String type_bigint = "bigint";
 
    private final String type_text = "text";
 
    private final String type_bit = "bit";
 
    private final String type_decimal = "decimal";
 
    private final String type_blob = "blob";
 
    private String table_name = "";
    
    private String model_package = "";
    
    private String mapper_package = "";
    
    private String model_path = "";
 
    private String mapper_path = "";
 
    private String model_xml_path = "";
 
    private String driverName = "";
 
    private String user = "";
 
    private String password = "";
 
    private String url = "";
    
    private String db_name = null;
    
    private String create_user = null;
 
    private String tableName = null;
 
    private String beanName = null;
 
    private String mapperName = null;
    
    private Connection conn = null;
    
    private String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
 
 
    private void init() throws ClassNotFoundException, SQLException {
    	Properties prop = new Properties();     
    	try{
    		File f = new File(this.getClass().getResource("/").getPath());
    		//读取属性文件a.properties
    		InputStream in = new BufferedInputStream (new FileInputStream(f.getAbsolutePath()+File.separator+"MybatisMapper.properties"));
    		prop.load(in);

    		driverName = prop.getProperty("jdbc.driver");
    		url = prop.getProperty("jdbc.url");
    		user = prop.getProperty("jdbc.username");
    		password = prop.getProperty("jdbc.password");
    		db_name = prop.getProperty("db.name");
    		table_name = prop.getProperty("table.name");
    		model_package = prop.getProperty("model.package");
    		mapper_package = prop.getProperty("mapper.package");
    		model_path = prop.getProperty("model.path");
    		mapper_path = prop.getProperty("mapper.path");
    		create_user = prop.getProperty("create.user");
    		model_xml_path = mapper_path+File.separator+"xml";
    		
    		
    		///加载属性列表
//    		Iterator<String> it=prop.stringPropertyNames().iterator();
//    		while(it.hasNext()){
//    			String key=it.next();
//    			System.out.println(key+":"+prop.getProperty(key));
//    		}
    		in.close();
    	}catch(Exception e){
    		System.out.println(e);
    	}	
    	
    	
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, user, password);
    }
 
 
    /**
     *  获取所有的表
     *
     * @return
     * @throws SQLException 
     */
    private List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<String>();
        String showTableSql = "";
        if("oracle.jdbc.OracleDriver".equals(driverName)){
        	if(table_name.indexOf("%") != -1){
        		showTableSql = "select * from user_tables where table_name like '"+table_name+"'";
        	}else{
        		showTableSql = "select * from user_tables where table_name ='"+table_name+"'";
        	}
        	
        }else if("com.mysql.jdbc.Driver".equals(driverName)){
        	if(table_name.indexOf("%") != -1){
        		showTableSql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '"+db_name+"' AND table_name like '"+table_name+"'";
        	}else{
        		showTableSql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '"+db_name+"' AND table_name ='"+table_name+"'";
        	}
        }
        PreparedStatement pstate = conn.prepareStatement(showTableSql);
        ResultSet results = pstate.executeQuery();
        while ( results.next() ) {
            String tableName = results.getString(1);
            //          if ( tableName.toLowerCase().startsWith("yy_") ) {
            tables.add(tableName);
            //          }
        }
        return tables;
    }
 
 
    private void processTable( String table ) {
        StringBuffer sb = new StringBuffer(table.length());
        String tableNew = table.toLowerCase();
        if(tableNew.indexOf("_")!=-1){
        	String[] tables = tableNew.split("_");
            String temp = null;
            int i=1;
            if(tableNew.startsWith("sys") || tableNew.startsWith("SYS")){
            	i=0;
            }
            for (;i < tables.length ; i++ ) {
                temp = tables[i].trim();
                if(!"".equals(temp)){
                	sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
                }
            }
        }else{
        	sb.append(tableNew.substring(0, 1).toUpperCase()).append(tableNew.substring(1));
        }
        
        beanName = sb.toString();
        mapperName = beanName + "Mapper";
    }
 
 
    private String processType( String type ) {
        if ( type.indexOf(type_char) > -1 ) {
            return "String";
        } else if ( type.indexOf(type_bigint) > -1 ) {
            return "Long";
        } else if ( type.indexOf(type_int) > -1 ) {
            return "Integer";
        } else if ( type.indexOf(type_date) > -1 ) {
            return "java.util.Date";
        } else if ( type.indexOf(type_text) > -1 ) {
            return "String";
        } else if ( type.indexOf(type_timestamp) > -1 ) {
            return "java.util.Date";
        } else if ( type.indexOf(type_bit) > -1 ) {
            return "Boolean";
        } else if ( type.indexOf(type_decimal) > -1 ) {
            return "java.math.BigDecimal";
        } else if ( type.indexOf(type_blob) > -1 ) {
            return "byte[]";
        }
        return null;
    }
 
 
    private String processField( String field ) {
        StringBuffer sb = new StringBuffer(field.length());
        //field = field.toLowerCase();
        String[] fields = field.split("_");
        String temp = null;
        sb.append(fields[0]);
        for ( int i = 1 ; i < fields.length ; i++ ) {
            temp = fields[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        return sb.toString();
    }
 
 
    /**
     *  将实体类名首字母改为小写
     *
     * @param beanName
     * @return 
     */
    private String processResultMapId( String beanName ) {
        return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
    }
 
 
    /**
     *  构建类上面的注释
     *
     * @param bw
     * @param text
     * @return
     * @throws IOException 
     */
    private BufferedWriter buildClassComment(BufferedWriter bw, String text) throws IOException {
        bw.newLine();
        bw.newLine();
        bw.write("/**");
        bw.newLine();
        bw.write(" * ");
        bw.newLine();
        bw.write(" * " + text);
        bw.newLine();
        bw.write(" * ");
        bw.newLine();
        bw.write(" * @author " + create_user);
        bw.newLine();
        bw.write(" * @since " + today);
        bw.newLine();
        bw.write(" * ");
        bw.newLine();
        bw.write(" **/");
        
        
        return bw;
    }
 
 
    /**
     *  构建方法上面的注释
     *
     * @param bw
     * @param text
     * @return
     * @throws IOException 
     */
    private BufferedWriter buildMethodComment( BufferedWriter bw, String text ) throws IOException {
        bw.newLine();
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t * ");
        bw.newLine();
        bw.write("\t * " + text);
        bw.newLine();
        bw.write("\t * ");
        bw.newLine();
        bw.write("\t **/");
        return bw;
    }
 
 
    /**
     *  生成实体类
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException 
     */
    private void buildEntityBean( List<String> columns, List<String> types, List<String> comments, String tableComment )
        throws IOException {
        
        File beanFile = new File(model_path, beanName + ".java");
        File dir = beanFile.getParentFile();  
        if (!dir.exists()) {  
            dir.mkdirs();  
        }  
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        bw.write("package " + model_package + ";");
        bw.newLine();
        bw.write("import java.io.Serializable;");
        bw.newLine();
        //bw.write("import lombok.Data;");
        //      bw.write("import javax.persistence.Entity;");
        bw = buildClassComment(bw, tableComment);
        bw.newLine();
        //      bw.write("@Entity");
        //bw.write("@Data");
        //bw.newLine();
        bw.write("public class " + beanName + " implements Serializable {");
        bw.newLine();
        bw.newLine();
        bw.write("\tprivate static final long serialVersionUID = 1L;");
        bw.newLine();
        bw.newLine();
        int size = columns.size();
        for ( int i = 0 ; i < size ; i++ ) {
            bw.write("\t/**" + comments.get(i) + "**/");
            bw.newLine();
            bw.write("\tprivate " + processType(types.get(i)) + " " + processField(columns.get(i)) + ";");
            bw.newLine();
            bw.newLine();
        }
        bw.newLine();
        // 生成get 和 set方法
        String tempField = null;
        String _tempField = null;
        String tempType = null;
        for ( int i = 0 ; i < size ; i++ ) {
            tempType = processType(types.get(i));
            _tempField = processField(columns.get(i));
            tempField = _tempField.substring(0, 1).toUpperCase() + _tempField.substring(1);
            bw.newLine();
            //          bw.write("\tpublic void set" + tempField + "(" + tempType + " _" + _tempField + "){");
            bw.write("\tpublic void set" + tempField + "(" + tempType + " " + _tempField + "){");
            bw.newLine();
            //          bw.write("\t\tthis." + _tempField + "=_" + _tempField + ";");
            bw.write("\t\tthis." + _tempField + " = " + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();
            bw.write("\tpublic " + tempType + " get" + tempField + "(){");
            bw.newLine();
            bw.write("\t\treturn this." + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
        }
        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
    }
 
 
    /**
     *  构建Mapper文件
     *
     * @throws IOException 
     */
    private void buildMapper() throws IOException {
        File folder = new File(mapper_path);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
 
        File mapperFile = new File(mapper_path, mapperName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        bw.write("package " + mapper_package + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import " + model_package + "." + beanName + ";");
        bw.newLine();
        bw.write("import java.util.Map;");
        bw.newLine();
        bw.write("import java.util.ArrayList;");
        
        bw = buildClassComment(bw, mapperName + "数据库操作接口类");
        bw.newLine();
        //      bw.write("public interface " + mapperName + " extends " + mapper_extends + "<" + beanName + "> {");
        bw.write("public interface " + mapperName + "{");
        bw.newLine();
        // ----------定义Mapper中的方法Begin----------
        bw = buildMethodComment(bw, "查询（根据主键ID查询）");
        bw.newLine();
        bw.write("\t" + beanName + " selectByPrimaryKey(Long id);");
        bw.newLine();
        bw = buildMethodComment(bw, "查询（根据条件查询）");
        bw.newLine();
        bw.write("\t" + "int selectListCount(Map<String,Object> map);");
        bw.newLine();
        bw = buildMethodComment(bw, "查询（根据条件查询）");
        bw.newLine();
        bw.write("\tArrayList<" + beanName + "> selectList(Map<String,Object> map);");
        bw.newLine();
        bw = buildMethodComment(bw, "添加");
        bw.newLine();
        bw.write("\t" + "int insert(" + beanName + " entity);");
        bw.newLine();
        bw = buildMethodComment(bw, "修改");
        bw.newLine();
        bw.write("\t" + "int update(" + beanName + " entity);");
        bw.newLine();
        bw = buildMethodComment(bw, "删除（根据主键ID删除）");
        bw.newLine();
        bw.write("\t" + "int deleteByPrimaryKey(Long id);");
        bw.newLine();
 
        // ----------定义Mapper中的方法End----------
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }
 
 
    /**
     *  构建实体类映射XML文件
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException 
     */
    private void buildMapperXml( List<String> columns, List<String> types, List<String> comments ) throws IOException {
        File folder = new File(model_xml_path);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
 
        File mapperXmlFile = new File(model_xml_path, mapperName + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
        bw.newLine();
        bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        bw.write("<mapper namespace=\"" + mapper_package + "." + mapperName + "\">");
        bw.newLine();
        bw.newLine();
 
        /*bw.write("\t<!--实体映射-->");
        bw.newLine();
        bw.write("\t<resultMap id=\"" + this.processResultMapId(beanName) + "ResultMap\" type=\"" + beanName + "\">");
        bw.newLine();
        bw.write("\t\t<!--" + comments.get(0) + "-->");
        bw.newLine();
        bw.write("\t\t<id property=\"" + this.processField(columns.get(0)) + "\" column=\"" + columns.get(0) + "\" />");
        bw.newLine();
        int size = columns.size();
        for ( int i = 1 ; i < size ; i++ ) {
            bw.write("\t\t<!--" + comments.get(i) + "-->");
            bw.newLine();
            bw.write("\t\t<result property=\""
                    + this.processField(columns.get(i)) + "\" column=\"" + columns.get(i) + "\" />");
            bw.newLine();
        }
        bw.write("\t</resultMap>");
 
        bw.newLine();
        bw.newLine();
        bw.newLine();*/
 
        // 下面开始写SqlMapper中的方法
        // this.outputSqlMapperMethod(bw, columns, types);
        buildSQL(bw, columns, types);
 
        bw.write("</mapper>");
        bw.flush();
        bw.close();
    }
 
 
    private void buildSQL( BufferedWriter bw, List<String> columns, List<String> types ) throws IOException {
        int size = columns.size();
        // 通用结果列
        bw.write("\t<!-- 通用查询结果列-->");
        bw.newLine();
        bw.write("\t<sql id=\"base_column_list\">");
        bw.newLine();
 
        bw.write("\t\tid,");
        for ( int i = 1 ; i < size ; i++ ) {
            bw.write(" " + columns.get(i));
            if ( i != size - 1 ) {
                bw.write(",");
            }
        }
 
        bw.newLine();
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();
 
        // 通用查询条件
        bw.write("\t<!--通用查询条件-->");
        bw.newLine();
        bw.write("\t<sql id=\"where_sql\">");
        bw.newLine();
        for ( int i = 1 ; i < size ; i++ ) {
        	bw.write("\t\t<if test=\""+columns.get(i)+" != null\">");
        	bw.newLine();
            bw.write("\t\t\tAND "+columns.get(i)+" =#{"+columns.get(i)+"}");
            bw.newLine();
            bw.write("\t\t</if>");
            bw.newLine();
        }
        
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();
    		
 
        // 查询（根据主键ID查询）
        bw.write("\t<!--查询（根据主键ID查询） -->");
        bw.newLine();
        bw.write("\t<select id=\"selectByPrimaryKey\" resultType=\""
                + processResultMapId(beanName) + "\" parameterType=\"java.lang." + processType(types.get(0)) + "\">");
        bw.newLine();
        bw.write("\t\tSELECT");
        bw.newLine();
        bw.write("\t\t<include refid=\"base_column_list\" />");
        bw.newLine();
        bw.write("\t\tFROM " + tableName);
        bw.newLine();
        bw.write("\t\tWHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        // 查询完成
 
        // 查询（根据条件查询总数 ）
        bw.write("\t<!--查询（根据条件查询总数） -->");
        bw.newLine();
        bw.write("\t<select id=\"selectListCount\" parameterType=\"java.util.Map\" resultType=\"int\" >");
        bw.newLine();
        bw.write("\t\tSELECT count(1)");
        bw.newLine();
        bw.write("\t\tFROM " + tableName);
        bw.newLine();
        bw.write("\t\t<trim");
        bw.newLine();
        bw.write("\t\t\tprefix=\"WHERE\"");
        bw.newLine();
        bw.write("\t\t\tprefixOverrides=\"AND |OR\" >");
        bw.newLine();
        bw.write("\t\t<include refid=\"where_sql\"/>");
        bw.newLine();
        bw.write("\t\t</trim>");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        // 查询（根据条件查询总数 ）完成
        
        // 查询（根据条件查询列表 ）
        bw.write("\t<!--查询（根据条件查询列表） -->");
        bw.newLine();
        bw.write("\t<select id=\"selectList\" parameterType=\"java.util.Map\" resultType=\""+processResultMapId(beanName)+"\" >");
        bw.newLine();
        bw.write("\t\tSELECT");
        bw.newLine();
        bw.write("\t\t<include refid=\"base_column_list\"/>");
        bw.newLine();
        bw.write("\t\tFROM sys_user");
        bw.newLine();
        bw.write("\t\t<trim");
        bw.newLine();
        bw.write("\t\t\tprefix=\"WHERE\"");
        bw.newLine();
        bw.write("\t\t\tprefixOverrides=\"AND |OR\" >");
        bw.newLine();
        bw.write("\t\t\t<include refid=\"where_sql\"/>");
        bw.newLine();
        bw.write("\t\t</trim>");
        bw.newLine();
        bw.write("\t\t<if test=\"start != null and limit != null\">");
        bw.newLine();
        bw.write("\t\tlimit #{start},#{limit}");
        bw.newLine();
        bw.write("\t\t</if>");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        // 查询（根据条件查询列表 ）完成
        
        //---------------  insert方法（匹配有值的字段）
        bw.write("\t<!-- 添加  -->");
        bw.newLine();
        bw.write("\t<insert id=\"insert\" parameterType=\"" + processResultMapId(beanName) + "\">");
        bw.newLine();
        bw.write("\t\tINSERT INTO " + tableName);
        bw.newLine();
        bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();
 
        String tempField = null;
        for ( int i = 0 ; i < size ; i++ ) {
            tempField = processField(columns.get(i));
            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t " + columns.get(i) + ",");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
 
        bw.newLine();
        bw.write("\t\t</trim>");
        bw.newLine();
 
        bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();
 
        tempField = null;
        for ( int i = 0 ; i < size ; i++ ) {
            tempField = processField(columns.get(i));
            bw.write("\t\t\t<if test=\"" + tempField + "!=null\">");
            bw.newLine();
            bw.write("\t\t\t\t #{" + tempField + "},");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
 
        bw.write("\t\t</trim>");
        bw.newLine();
        bw.write("\t</insert>");
        bw.newLine();
        bw.newLine();
        //---------------  完毕
 
 
        // 修改update方法
        bw.write("\t<!-- 修 改 -->");
        bw.newLine();
        bw.write("\t<update id=\"update\" parameterType=\"" + processResultMapId(beanName) + "\">");
        bw.newLine();
        bw.write("\t\tUPDATE " + tableName);
        bw.newLine();
        bw.write(" \t\t<set> ");
        bw.newLine();
 
        tempField = null;
        for ( int i = 1 ; i < size ; i++ ) {
            tempField = processField(columns.get(i));
            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t " + columns.get(i) + " = #{" + tempField + "},");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
 
        bw.newLine();
        bw.write(" \t\t</set>");
        bw.newLine();
        bw.write("\t\tWHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();
        bw.newLine();
        // update方法完毕
        
        // 删除（根据主键ID删除）
        bw.write("\t<!-- 删除：根据主键ID删除 -->");
        bw.newLine();
        bw.write("\t<delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang." + processType(types.get(0)) + "\">");
        bw.newLine();
        bw.write("\t\tDELETE FROM " + tableName);
        bw.newLine();
        bw.write("\t\tWHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        bw.newLine();
        // 删除完
    }
 
 
    /**
     *  获取所有的数据库表注释
     *
     * @return
     * @throws SQLException 
     */
    private Map<String, String> getTableComment() throws SQLException {
        Map<String, String> maps = new HashMap<String, String>();
        if("oracle.jdbc.OracleDriver".equals(driverName)){
        	PreparedStatement pstate = conn.prepareStatement("select table_name,comments from user_tab_comments");
            ResultSet results = pstate.executeQuery();
            while ( results.next() ) {
                String tableName = results.getString("table_name");
                String comment = results.getString("comments");
                if(null==comment){
                	comment = "";
                }
                maps.put(tableName, comment);
            }
        }else if("com.mysql.jdbc.Driver".equals(driverName)){
        	PreparedStatement pstate = conn.prepareStatement("show table status");
            ResultSet results = pstate.executeQuery();
            while ( results.next() ) {
                String tableName = results.getString("NAME");
                String comment = results.getString("COMMENT");
                if(null==comment){
                	comment = "";
                }
                maps.put(tableName, comment);
            }
        }
        
        return maps;
    }
 
 
    public void generate() throws ClassNotFoundException, SQLException, IOException {
        init();
        if("oracle.jdbc.OracleDriver".equals(driverName)){
        	String prefix = "SELECT DISTINCT a.table_name,a.data_type,b.column_name,b.comments FROM all_tab_cols a"
        			+ " LEFT JOIN user_col_comments b ON a.table_name=b.table_name where a.table_name='";
            List<String> columns = null;
            List<String> types = null;
            List<String> comments = null;
            PreparedStatement pstate = null;
            List<String> tables = getTables();
            Map<String, String> tableComments = getTableComment();
            for ( String table : tables ) {
                columns = new ArrayList<String>();
                types = new ArrayList<String>();
                comments = new ArrayList<String>();
                pstate = conn.prepareStatement(prefix + table + "'");
                ResultSet results = pstate.executeQuery();
                while ( results.next() ) {
                    columns.add(results.getString("column_name"));
                    types.add(results.getString("data_type"));
                    String comment = results.getString("comments");
                	if(null==comment){
                    	comment = "";
                    }
                    comments.add(comment);
                }
                tableName = table;
                processTable(table);
                //          this.outputBaseBean();
                String tableComment = tableComments.get(tableName);
                buildEntityBean(columns, types, comments, tableComment);
                buildMapper();
                buildMapperXml(columns, types, comments);
            }
        	
        }else if("com.mysql.jdbc.Driver".equals(driverName)){
        	String prefix = "show full fields from ";
            List<String> columns = null;
            List<String> types = null;
            List<String> comments = null;
            PreparedStatement pstate = null;
            List<String> tables = getTables();
            Map<String, String> tableComments = getTableComment();
            for ( String table : tables ) {
                columns = new ArrayList<String>();
                types = new ArrayList<String>();
                comments = new ArrayList<String>();
                pstate = conn.prepareStatement(prefix + table);
                ResultSet results = pstate.executeQuery();
                while ( results.next() ) {
                    columns.add(results.getString("FIELD"));
                    types.add(results.getString("TYPE"));
                    comments.add(results.getString("COMMENT"));
                }
                tableName = table;
                processTable(table);
                //          this.outputBaseBean();
                String tableComment = tableComments.get(tableName);
                buildEntityBean(columns, types, comments, tableComment);
                buildMapper();
                buildMapperXml(columns, types, comments);
            }
        }
        
        
        conn.close();
        System.out.println("mysql文件生成结束！");
    }
 
 
    public static void main( String[] args ) {
        try {
            new MybatisMapperUtil().generate();
            
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        } catch ( SQLException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
