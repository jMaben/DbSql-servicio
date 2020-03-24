package com.app.dbsql.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.dbsql.service.IService;

import app.commons.models.entity.Connections;

@Controller
@RequestMapping("/sql")
public class ControlClass {

	@Autowired
	private IService connections;

	@GetMapping("/tables")
	public ResponseEntity<List<String>> findTablesAll() throws ClassNotFoundException, SQLException {
		Connections connection = new Connections();
		connection = convertToConnection();
		return new ResponseEntity<List<String>>(connections.getTablesAll(connection), HttpStatus.OK);
	}

	public Connections convertToConnection() {
		Connections connection = new Connections();
		connection.setHost("localhost");
		connection.setPort(3306);
		connection.setUser("root");
		connection.setPass("root");
		connection.setAlias("erd_connections");
		return connection;
	}
}
