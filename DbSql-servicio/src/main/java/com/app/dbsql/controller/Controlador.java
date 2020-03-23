package com.app.dbsql.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.dbsql.service.IServicio;

@Controller
@RequestMapping("/connections")
public class Controlador {

	@Autowired
	private IServicio connections;
	
	@GetMapping("/prueba")
	public ResponseEntity<List<String>> findAllTables() throws ClassNotFoundException, SQLException{
		return new ResponseEntity<List<String>>(connections.getTablas(),HttpStatus.OK);
	}
	
}
