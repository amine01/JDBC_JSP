package com.essamine.listners;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ClassListner implements ServletContextListener, ServletContextAttributeListener {

	int counter;
	String counterFilePath = "c:\\counter.txt";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(counterFilePath));
			counter = Integer.parseInt(bufferReader.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ServletContext sc = sce.getServletContext();
		sc.setAttribute("pcounter", Integer.toString(counter));
		System.out.println("Application initialized");

		sc.setAttribute("globalAttribute", "Global Attribute");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("contextDestroyed");

	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent arg0) {
		System.out.println("ServletContext attribute added");

	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		System.out.println("ServletContext attribute removed");

	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		System.out.println("ServletContext attribute replaced");

		writeCounter(arg0);
	}

	synchronized void writeCounter(ServletContextAttributeEvent scae) {
		ServletContext servletContext = scae.getServletContext();

		counter = Integer.parseInt((String) servletContext.getAttribute("pcounter"));

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(counterFilePath));
			writer.write(Integer.toString(counter));
			writer.close();
			System.out.println("Writing");
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}
