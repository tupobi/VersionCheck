package com.tupobi.android;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tupobi.bean.AppVersion;
import com.tupobi.biz.AppVersionCheckBizImpl;
import com.tupobi.biz.IAppVersionCheckBiz;
import com.tupobi.utils.Config;
import com.tupobi.utils.JsonUtil;

public class CheckVersion extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		IAppVersionCheckBiz appVersionCheckBiz = new AppVersionCheckBizImpl();
		String appName = request.getParameter("appName");
		AppVersion latestAppVersion = appVersionCheckBiz.checkAppVersion(appName);
		if (latestAppVersion != null) {
			out.write(JsonUtil.bean2Json(latestAppVersion));
		} else {
			out.write(Config.NO_RESULT);
		}

		out.flush();
		out.close();
	}

}
