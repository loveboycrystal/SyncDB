package com.loveboy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * 初始化系统配置文件工具类
 * @author Chenes
 *
 */
public class InitMain {
	protected static  Logger log = Logger.getLogger(InitMain.class);
	
	//是否测试环境 true 为测试，读取src/config/文件； false 读取jar包同级别路径配置文件 ,并且不删除zip文件
	public static boolean IS_TEST = false;
	
	
	private  final static String initPropertiesName = "init.properties";
	
	//properties配置文件
	private static Properties initPro = null;
	
	//获取配置properties文件属性
	public static String getInitProp(String key) {
		String attr = "";
		try {
			attr = initPro.getProperty(key);
			if(attr==null){
				attr="";
				log.error("在"+initPropertiesName+"获取"+key+"未找到.");
			}
		} catch (Exception e) {
			attr = "";			log.error("在"+initPropertiesName+"获取"+key+"未找到.");
		}
		return attr.toString();
	}
	
	
	static{
		log.info("加载系统InitMain资源中(init system initmain source)...");
		File  file = null;
		if(IS_TEST){
			log.info("当前为测试环境(the current is tester).");
			file = new File("src/config/init.properties");
		}else{
			log.info("当前为jar运行环境(the current is jar).");
			String jarpath=new String(InitMain.class.getProtectionDomain().getCodeSource().getLocation().getFile());
			jarpath = jarpath.substring(0, jarpath.lastIndexOf("/") + 1);  
			file = new File(jarpath+initPropertiesName);
		}
		InputStream in;
		try {
			in = new FileInputStream(file);
			initPro = new Properties();
			initPro.load(in);
			
			log.info(initPropertiesName+"文件加载成功,共"+initPro.size()+"条("+initPropertiesName+" file was load finish).");
		} catch (FileNotFoundException e) {
			log.error("系统找不到初始化文件"+initPropertiesName+"。"+e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("加载"+initPropertiesName+"错误。"+e.toString());
			e.printStackTrace();
		}
		
	}
	
	

}
