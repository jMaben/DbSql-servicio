package com.app.dbsql.service;

import java.sql.SQLException;
import java.util.List;

import app.commons.models.entity.Connections;
import app.commons.models.entity.Table;

public interface IService {
	public List<String> getTablesAll(Connections connec) throws ClassNotFoundException, SQLException ;
	public Table getAllOneTable(Connections connec, String table) throws ClassNotFoundException, SQLException ;
}
