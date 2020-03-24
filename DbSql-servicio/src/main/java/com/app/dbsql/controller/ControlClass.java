package com.app.dbsql.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.dbsql.service.IService;

import app.commons.models.entity.Connections;

@Controller
@RequestMapping("/sql")
public class ControlClass {

	@Autowired
	private IService connections;

	
	@CrossOrigin
	@GetMapping("/tables/{host}/{port}/{user}/{pass}/{alias}")
	public ResponseEntity<List<String>> findTablesAll(@PathVariable String host, @PathVariable Integer port,
			@PathVariable String user, @PathVariable String pass, @PathVariable String alias)
			throws ClassNotFoundException, SQLException {
		Connections connection = new Connections();
		System.out.println(host + port +user +pass + alias);
		connection = convertToConnection(host, port, user, pass, alias);
		return new ResponseEntity<List<String>>(connections.getTablesAll(connection), HttpStatus.OK);
	}

	public Connections convertToConnection(String host, Integer port, String user, String pass, String alias) {
		Connections connection = new Connections();
		connection.setHost(host);
		connection.setPort(port);
		connection.setUser(user);
		connection.setPass(pass);
		connection.setAlias(alias);
		return connection;
	}
}
