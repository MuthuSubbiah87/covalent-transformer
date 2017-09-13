package com.covalent.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonUtils {
	public static Properties loadProperyOut(String filename) {
		Properties prop = new Properties();
	    try {
	        File jarPath=new File(CommonUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        String propertiesPath = jarPath.getPath().substring(0,jarPath.getPath().length() - 47);
	        String finalPath = propertiesPath.substring(5, propertiesPath.length());
	        File prp = new File(finalPath + filename);
	        System.out.println(" propertiesPath-" + finalPath + filename);
	        prop.load(new FileInputStream(prp));
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }
		return prop;
	}

}
