package com.sanver.trials.springboot_hello_rest_source;

public class GreetingDTO {
	private String greeting;
	private long time;
	private String ip;

	public GreetingDTO(String greeting, long time, String ip) {
		this.greeting = greeting;
		this.time = time;
		this.ip = ip;
	}

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}