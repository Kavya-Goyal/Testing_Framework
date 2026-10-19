package io.learn.utils;

public enum Browser {
	CHROME("chrome"),
	EDGE("edge"),
	FIREFOX("firefox");
	
	private final String browserName;

	Browser(String name) {
		browserName = name;
	}

	public String getBrowserName() {
		return browserName;
	}
	
	
}
