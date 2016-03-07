package com.essamine.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageCounterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Page Counter</TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		ServletContext servletContext = getServletContext();
		int pageCounter = Integer.parseInt((String) servletContext.getAttribute("pcounter"));
		pageCounter++;
		out.println("You are visitor number " + pageCounter);
		servletContext.setAttribute("pcounter", Integer.toString(pageCounter));

		out.println("</BODY>");
		out.println("</HTML>");
	}
}