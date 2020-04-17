package com.app.dbsql.controller;

import java.sql.SQLException;
import java.util.ArrayList;
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
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.app.commons.models.entity.Connections;
import com.springboot.app.commons.models.entity.Table;



@Controller
@RequestMapping("/sql")
public class ControlClass {

	@Autowired
	private IService connections;

	/**
	 * El siguente metodo recibe una serie de parametros para convertirlos en un objeto Connection
	 * utilizando un metodo externo. Para despues retornar el resultado de una consulta, de nuevo
	 * usando un metodo externo para realizar la consulta.
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param pass
	 * @param alias
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "alternativeMethod")
	@CrossOrigin
	@GetMapping("/tables/{host}/{port}/{user}/{pass}/{alias}")
	public ResponseEntity<List<String>> findTablesAll(@PathVariable String host, @PathVariable Integer port,
			@PathVariable String user, @PathVariable String pass, @PathVariable String alias)
			throws ClassNotFoundException, SQLException {
		Connections connection = new Connections();
		connection = convertToConnection(host, port, user, pass, alias);
		return new ResponseEntity<List<String>>(connections.getTablesAll(connection), HttpStatus.OK);
	}

	/**
	 * Este es un metodo alternativo al metodo de findTablesAll, el cual retorna un bad request de Http.
	 *
	 * @param host
	 * @param port
	 * @param user
	 * @param pass
	 * @param alias
	 * @return
	 */
	@CrossOrigin
	public ResponseEntity<List<String>> alternativeMethod(String host, Integer port, String user, String pass, String alias) {
		List<String> res = new ArrayList<String>();
		return new ResponseEntity<List<String>>(res, HttpStatus.BAD_REQUEST);	
	}
	
	/**
	 * Este metodo crea un objeto Connection utilizando los parametros que se le pasan.
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param pass
	 * @param alias
	 * @return
	 */
	public Connections convertToConnection(String host, Integer port, String user, String pass, String alias) {
		Connections connection = new Connections();
		connection.setHost(host);
		connection.setPort(port);
		connection.setUser(user);
		connection.setPass(pass);
		connection.setAlias(alias);
		return connection;
	}
	
	@CrossOrigin
	@GetMapping("/allOfTable/{host}/{port}/{user}/{pass}/{alias}/{table}")
	public ResponseEntity<Table> findTableAllData(@PathVariable String host, @PathVariable Integer port,
			@PathVariable String user, @PathVariable String pass, @PathVariable String alias, @PathVariable String table)
			throws ClassNotFoundException, SQLException {
		Connections connection = new Connections();
		connection = convertToConnection(host, port, user, pass, alias);
		return new ResponseEntity<Table>(connections.getAllOneTable(connection, table), HttpStatus.OK);
	}
}
