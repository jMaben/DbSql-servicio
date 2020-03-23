package com.app.dbsql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"app.commons.models.entity"})
public class DbSqlServicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbSqlServicioApplication.class, args);
	}

}
