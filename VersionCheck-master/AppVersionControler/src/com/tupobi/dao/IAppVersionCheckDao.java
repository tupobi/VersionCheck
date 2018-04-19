package com.tupobi.dao;

import java.sql.SQLException;

import com.tupobi.bean.AppVersion;

public interface IAppVersionCheckDao {
	
	public AppVersion getLatestVersion(String appName) throws SQLException;

}
