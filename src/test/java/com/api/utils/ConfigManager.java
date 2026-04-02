package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class ConfigManager {
	
	private static Properties prop = new Properties();//create object of the properties class
	private static String path = "config\\config.properties";
	private static String env;
	private ConfigManager() {
	
	}
	
	static {
		
		env = System.getProperty("env","qa");
		env = env.toLowerCase().trim();
		switch(env) {
		case "dev" -> path = "config\\config.dev.properties";

		case "qa" -> path = "config\\config.qa.properties";
			
		case "uat" -> path =  "config\\config.uat.properties";

		default -> path = "config\\config.qa.properties";
		
		}
		
		
//		File configFile = new File(System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"
//				+File.separator+"resources"+File.separator+"config"+File.separator+"config.properties");
//		FileReader fileReader = null;
		
		 InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		 
		 if(input == null) {
			 
			 throw new RuntimeException("Can not find the file at the path : "+path);
			
		 }
		
		try {
//			fileReader = new FileReader(configFile);
//			prop.load(fileReader);		
			prop.load(input);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getProperty(String key) throws IOException {
		
		//Load the properties file using the load()
		return(prop.getProperty(key));
	}

}
