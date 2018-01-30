package com.loveboy.main;

import org.apache.log4j.Logger;

import com.loveboy.thread.DoWork;

public class Enter {
	protected static  Logger log = Logger.getLogger(Enter.class);
	
	public static void main(String[] args) {
		new DoWork().start();
	}
}
