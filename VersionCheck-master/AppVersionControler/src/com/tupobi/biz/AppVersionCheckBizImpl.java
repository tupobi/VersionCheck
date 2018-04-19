package com.tupobi.biz;

import java.sql.SQLException;

import com.tupobi.bean.AppVersion;
import com.tupobi.dao.AppVersionCheckDaoImpl;
import com.tupobi.dao.IAppVersionCheckDao;
import com.tupobi.utils.Config;

public class AppVersionCheckBizImpl implements IAppVersionCheckBiz{
	private IAppVersionCheckDao appVersionCheckDao;
	
	public AppVersionCheckBizImpl(){
		appVersionCheckDao = new AppVersionCheckDaoImpl();
	}

	@Override
	public AppVersion checkAppVersion(String appName) {
		try {
			AppVersion latestAppVersion = appVersionCheckDao.getLatestVersion(appName);
			if(latestAppVersion == null){
				return null;
			}else{
				return latestAppVersion;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
