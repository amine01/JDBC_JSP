package com.essamine.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ClassListner implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("contextDestroyed");

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("contextInitialized");

	}

}
