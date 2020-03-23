package com.app.dbsql.service;

import java.sql.SQLException;
import java.util.List;

public interface IServicio {
	public List<String> getTablas() throws ClassNotFoundException, SQLException ;
}
