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

@Service
public class ImpServicio implements IServicio {
	private Connection kon;
	private PreparedStatement preparedStatement;
	private Connections connec = new Connections();

	private void conectar() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		editarConnec();
		String url = "jdbc:mysql://" + connec.getHost() + "/" + connec.getAlias() + "?serverTimezone=UTC";
		kon = DriverManager.getConnection(url, connec.getUser(), connec.getPass());
	}

	private void editarConnec() {
		this.connec.setHost("localhost:3306");
		this.connec.setUser("root");
		this.connec.setPass("root");
		this.connec.setAlias("erd_connections");
	}

	private void desconectar() throws SQLException {
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (kon != null) {
			kon.close();
		}
	}

	@Override
	public List<String> getTablas() throws ClassNotFoundException, SQLException {
		ResultSet rs = null;
		List<String> prueba = new ArrayList<String>();

		try {
			conectar();
			System.out.println("Intento de conexion");
			String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = ?";
			preparedStatement = kon.prepareStatement(sql);
			preparedStatement.setString(1, connec.getAlias());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				prueba.add(rs.getString("table_name"));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			desconectar();
		}

		return prueba;
	}
}
