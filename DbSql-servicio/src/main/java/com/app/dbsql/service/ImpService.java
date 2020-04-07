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

import app.commons.models.entity.Column;
import app.commons.models.entity.Connections;
import app.commons.models.entity.Table;

@Service
public class ImpService implements IService {
	private Connection dbCon;
	private PreparedStatement preparedStatement;

	/**
	 * Este metodo intenta establecer una conexion, utilizando el objeto Connection
	 * que recibe.
	 * 
	 * @param connec
	 */
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

	/**
	 * Este metodo desconecta o termina una conexion previamente establecida.
	 */
	private void disconnect() throws SQLException {
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (dbCon != null) {
			dbCon.close();
		}
	}

	/**
	 * El siguente metedo utilizando un metodo externo establece una conexion a base
	 * de datos, despues hace una consulta la cual la guarda en una lista y
	 * finalmente desconecta la conexion con dicha base de datos a la cual se habia
	 * conectado.
	 * 
	 * @param connec
	 * @return
	 */
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

	@Override
	public Table getAllOneTable(Connections connec, String table) throws ClassNotFoundException, SQLException {
		ResultSet rs = null;
		List<String> tableList = new ArrayList<String>();
		ArrayList<Column> columnList = new ArrayList<Column>();
		Table objTable = new Table();
		objTable.setName(table);

		try {
			connect(connec);
			String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = ?";
			preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setString(1, connec.getAlias());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				tableList.add(rs.getString("table_name"));
			}
			if (tableExist(tableList, table) == true) {
				String getInfo = "SELECT * FROM " + table;
				rs = null;
				preparedStatement = dbCon.prepareStatement(getInfo);
				rs = preparedStatement.executeQuery();
				while (rs.next()) {

					for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
						System.out.print(" " + rs.getMetaData().getColumnName(i) + "=" + rs.getObject(i));
						Column column = new Column(rs.getMetaData().getColumnName(i),rs.getObject(i));
						columnList.add(column);
					}

				}
			objTable.setColumns(columnList);
			}
		} finally {

			disconnect();

		}
		return objTable;
	}

	public boolean tableExist(List<String> tableList, String table) {
		Boolean exist = false;
		for (int i = 0; i < tableList.size(); i++) {
			if (tableList.get(i).contentEquals(table) == true) {
				exist = true;
			}
		}
		return exist;

	}

}
