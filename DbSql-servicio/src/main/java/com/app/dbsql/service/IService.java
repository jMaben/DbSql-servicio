package com.app.dbsql.service;

import java.sql.SQLException;
import java.util.List;

import app.commons.models.entity.Connections;

public interface IService {
	public List<String> getTablesAll(Connections connec) throws ClassNotFoundException, SQLException ;
}
