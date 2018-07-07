package com.sanver.trials.springboot_hello_rest_source;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend")
@ConfigurationProperties(prefix = "greeting")
public class GreetingController {

	private String saying;
	private String backendServiceHost;
	private int backendServicePort;

	public void setSaying(String saying) {
		this.saying = saying;
	}

	public void setBackendServiceHost(String backendServiceHost) {
		this.backendServiceHost = backendServiceHost;
	}

	public void setBackendServicePort(int backendServicePort) {
		this.backendServicePort = backendServicePort;
	}

	public String getSaying() {
		return saying;
	}

	public String getBackendServiceHost() {
		return backendServiceHost;
	}

	public int getBackendServicePort() {
		return backendServicePort;
	}

	@RequestMapping("/greeting")
	public GreetingDTO greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		String backendServiceUrl = String.format("http://%s:%d/hello?greeting={greeting}", backendServiceHost,
				backendServicePort);
		System.out.println("Sending to: " + backendServiceUrl);
		return new GreetingDTO(
				String.format(saying, name) + " Message from " + backendServiceHost + ":" + backendServicePort,
				LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), getIp());
	}

	private String getIp() {
		String hostname = null;
		try {
			hostname = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			hostname = "unknown";
		}
		return hostname;
	}
}