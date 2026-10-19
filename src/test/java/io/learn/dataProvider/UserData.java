package io.learn.dataProvider;

import io.learn.exception.InvalidDataException;

public class UserData {
	public static Object[][] userData(){
		Object[][] data = {
				{"standard_user","secret_sauce",true},
				{"locked_out_user","secret_sauce",false},
				{"problem_user","secret_sauce",true},
				{"performance_glitch_user","secret_sauce",true},
				{"error_user","secret_sauce",true},
				{"visual_user","secret_sauce",true}
		};
		
		for(Object[] userData : data) {
			if(isInvalid(userData)) {
				throw new InvalidDataException("User not found.");
			}
		}
		
		return data;
	}
	
	public static boolean isInvalid(Object[] userData) {
		return userData[0] == null || userData[1] == null;
	}
}
