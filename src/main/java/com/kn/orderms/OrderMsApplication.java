package com.kn.orderms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@SpringBootApplication
@Slf4j
public class OrderMsApplication {


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(OrderMsApplication.class);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);
	}

	private static void logApplicationStartup(Environment env) {
		String protocol = Optional
				.ofNullable(env.getProperty("server.ssl.key-store"))
				.map(key -> "https")
				.orElse("http");
		String serverPort = Optional
				.ofNullable(env.getProperty("server.port"))
				.filter((s) -> !s.isBlank())
				.orElse(env.getProperty("local.server.port"));
		String contextPath = Optional
				.ofNullable(env.getProperty("server.servlet.context-path"))
				.filter((s) -> !s.isBlank())
				.orElse("/");

		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info(
				"""

						----------------------------------------------------------
						\tApplication '{}' is running! Access URLs:
						\tLocal: \t\t{}://localhost:{}{}
						\tExternal: \t{}://{}:{}{}
						\tProfile(s): \t{}
						----------------------------------------------------------""",
				env.getProperty("spring.application.name"),
				protocol, serverPort, contextPath,
				protocol, hostAddress, serverPort, contextPath,
				env.getProperty("spring.datasource.url")
		);
	}

}
