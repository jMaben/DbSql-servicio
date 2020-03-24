package com.app.dbsql.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import app.commons.models.entity.Connections;
import ch.qos.logback.classic.Logger;

@Service
public class ImpService implements IService {
	private Connection dbCon;
	private PreparedStatement preparedStatement;
	private Connections connec = new Connections();

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		editarConnec();
		String url = "jdbc:mysql://" + connec.getHost() + ":" + String.valueOf(connec.getPort()) + "/" + connec.getAlias() + "?serverTimezone=UTC";
		dbCon = DriverManager.getConnection(url, connec.getUser(), connec.getPass());
	}

	private void editarConnec() {
		this.connec.setHost("localhost");
		this.connec.setPort(3306);
		this.connec.setUser("root");
		this.connec.setPass("root");
		this.connec.setAlias("erd_connections");
	}

	private void disconnect() throws SQLException {
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (dbCon != null) {
			dbCon.close();
		}
	}

	@Override
	public List<String> getTablesAll() throws ClassNotFoundException, SQLException {
		ResultSet rs = null;
		List<String> tableList = new ArrayList<String>();

		try {
			connect();
			String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = ?";
			preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setString(1, connec.getAlias());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				tableList.add(rs.getString("table_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return tableList;
	}
}
