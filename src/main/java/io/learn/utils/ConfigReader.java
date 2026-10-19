package io.learn.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private final Properties properties;
	
	public ConfigReader() {
		properties = new Properties();
		try(InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")){
			if(input == null) {
				return;
			}
			properties.load(input);
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
