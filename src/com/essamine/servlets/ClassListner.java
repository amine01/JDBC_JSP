package com.essamine.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ClassListner implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("contextInitialized");
		ServletContext sc = sce.getServletContext();
		sc.setAttribute("globalAttribute", "Global Attribute");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("contextDestroyed");

	}

}
