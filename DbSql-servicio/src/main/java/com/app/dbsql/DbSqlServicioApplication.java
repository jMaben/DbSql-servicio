package com.app.dbsql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.springboot.app.commons.models.entity"})
public class DbSqlServicioApplication {

	/**
	 * Este metodo es el pricipal es el que inicia el programa
	 */
	public static void main(String[] args) {
		SpringApplication.run(DbSqlServicioApplication.class, args);
	}

}
