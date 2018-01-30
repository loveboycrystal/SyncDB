package com.loveboy.util;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
	
	protected static  Logger log = Logger.getLogger(SpringUtil.class);
	
	private SpringUtil(){}
	
	private static ClassPathXmlApplicationContext ctx=null;
	
	
	/**
	 * 初始化spring、mybatis、db pool
	 * @return
	 */
	private static ClassPathXmlApplicationContext initApplicationContext(){
		try {
			if(ctx == null){
				ctx = new ClassPathXmlApplicationContext( "classpath:config/applicationContext.xml");
			}
		} catch (Exception e) {
			log.error("请检查数据连接是否正常(db con)："+getExceptionMsg(e));
			e.printStackTrace();
		}
		return ctx;
	}
	
	
	
	/**
	 * 获取容器中的对象
	 * @param name
	 * @return
	 */
	public static Object getBean(String name){
		try {
			initApplicationContext();
			return ctx.getBean(name);
		} catch (Exception e) {
			log.error("容器不存在的对象名字(beanName not found)："+name+"\terror:"+getExceptionMsg(e));
		}
		return null;
	}
	
	
	
	 public static String getExceptionMsg(Exception e){
		 StringBuffer errMsg = new StringBuffer();
		 StackTraceElement ste =e.getStackTrace()[0];
		 errMsg.append(e.toString());
		 errMsg.append(" by class "+ste.getClassName()+"."+ste.getMethodName()+"()");
		 errMsg.append(" in err line number "+ste.getLineNumber());
		 return errMsg.toString();
		 
	 }
}
