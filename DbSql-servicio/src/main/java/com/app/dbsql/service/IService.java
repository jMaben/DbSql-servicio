package com.app.dbsql.service;

import java.sql.SQLException;
import java.util.List;

public interface IService {
	public List<String> getTablesAll() throws ClassNotFoundException, SQLException ;
}
