package com.loveboy.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultipleDataSource  extends AbstractRoutingDataSource{
	
	public final static String oaDataSourceKey = "oaDataSource";
	public final static String dcDataSourceKey = "dcDataSource";

	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }
	    
	@Override
	protected Object determineCurrentLookupKey() {
		return  dataSourceKey.get();
	}

}
