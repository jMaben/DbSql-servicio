package com.app.dbsql.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import app.commons.models.entity.Connections;

@Service
public class ImpService implements IService {
	private Connection dbCon;
	private PreparedStatement preparedStatement;

	private void connect(Connections connec) throws SQLException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://" + connec.getHost() + ":" + String.valueOf(connec.getPort()) + "/"
					+ connec.getAlias() + "?serverTimezone=UTC";
			dbCon = DriverManager.getConnection(url, connec.getUser(), connec.getPass());
		} catch (SQLException e) {
			throw new SQLException(e);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException();
		}

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
	public List<String> getTablesAll(Connections connec) throws ClassNotFoundException, SQLException {
		ResultSet rs = null;
		List<String> tableList = new ArrayList<String>();

		try {
			connect(connec);
			String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = ?";
			preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setString(1, connec.getAlias());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				tableList.add(rs.getString("table_name"));
			}
		} finally {

			disconnect();

		}
		return tableList;
	}

}
