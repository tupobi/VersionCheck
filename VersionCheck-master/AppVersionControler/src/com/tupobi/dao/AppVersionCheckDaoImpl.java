package com.tupobi.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.tupobi.bean.AppVersion;
import com.tupobi.utils.C3P0Util;

public class AppVersionCheckDaoImpl implements IAppVersionCheckDao{

	@Override
	public AppVersion getLatestVersion(String appName) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from version where appName = ? and isLatest = ?", new BeanHandler<AppVersion>(AppVersion.class), appName, true);
	}

}
