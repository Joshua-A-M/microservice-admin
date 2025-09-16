package com.chatservice.admin;

import com.chatservice.admin.config.ServiceConfig;
import com.chatservice.admin.utils.UserContextInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RefreshScope
@EnableRedisRepositories
@EnableDiscoveryClient
@SpringBootApplication
public class EmployeeserviceApplication {
	private static final Logger logger = LogManager.getLogger(EmployeeserviceApplication.class);
	@Autowired
	public ServiceConfig serviceConfig;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeserviceApplication.class, args);
		logger.info("\nApplication started — JSON logging enabled!"); // This triggers JSON file creation

	}



	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		String hostname = serviceConfig.getRedisServer();
		int port = Integer.parseInt(serviceConfig.getRedisPort());
		RedisStandaloneConfiguration redisStandaloneConfiguration
				 = new RedisStandaloneConfiguration(hostname, port);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){

		RestTemplate template = new RestTemplate();
		List interceptors = template.getInterceptors();
		if(interceptors == null){
			template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		}
		else {
			interceptors.add(new UserContextInterceptor());
			template.setInterceptors(interceptors);
		}
		return template;
	}


}
