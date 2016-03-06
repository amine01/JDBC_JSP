package com.essamine.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.essamine.entities.User;
import com.essamine.repositories.UserRepository;

public class ServletOne extends HttpServlet {

	/**
	 * 
	 */

	UserRepository userRepository = new UserRepository();
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		out = response.getWriter();
		out.print("<html><head>");

		if (request.getParameter("register") != null) {
			if (request.getParameter("edit") != null) {
				// edit user
				User user = userRepository.findOne(Integer.parseInt(request.getParameter("id")));

				out.print("<title>REGISTRATION PAGE</title></head>");
				out.print("<body>");
				out.print("<center>");
				out.print("<h1>Registration</h1>");
				out.print("<table><form action='servlet' method='POST'>");

				out.print("<input type='hidden' name='register'>");
				out.print("<input type='hidden' name='edit'>");
				out.print("<input type='hidden' name='id' value='" + user.getId() + "'>");

				out.print("<tr><td>FIRST NAME</td><td><input type='text' name='fname' value='" + user.getFirstname()
						+ "'></td></tr>");
				out.print("<tr><td>LAST NAME</td><td><input type='text' name='lname' value='" + user.getLastname()
						+ "'></td></tr>");
				out.print("<tr><td>USERNAME</td><td><input type='text' name='uname' value='" + user.getUsername()
						+ "'></td></tr>");
				out.print("<tr><td>PASSWORD</td><td><input type='password' name='passw' value='" + user.getPassword()
						+ "'></td></tr>");
				out.print("<tr><td></td><td><input type='submit' value='OK'></td></tr>");
				out.print("</form></table>");
				out.print("<hr><a href='servlet?all'>ALL USERS</a>");
				out.print("</center>");
			} else {
				// add user
				out.print("<title>REGISTRATION PAGE</title></head>");
				out.print("<body>");
				out.print("<center>");
				out.print("<h1>Registration</h1>");
				out.print("<table><form action='servlet' method='POST'>");
				out.print("<input type='hidden' name='register'>");
				out.print("<tr><td>FIRST NAME</td><td><input type='text' name='fname'></td></tr>");
				out.print("<tr><td>LAST NAME</td><td><input type='text' name='lname'></td></tr>");
				out.print("<tr><td>USERNAME</td><td><input type='text' name='uname'></td></tr>");
				out.print("<tr><td>PASSWORD</td><td><input type='password' name='passw'></td></tr>");
				out.print("<tr><td></td><td><input type='submit' value='OK'></td></tr>");
				out.print("</form></table>");
				out.print("<hr><a href='servlet?all'>ALL USERS</a>");
				out.print("</center>");
			}

		} else if (request.getParameter("all") != null) {

			HttpSession session = request.getSession();
			if (session == null) {
				response.sendRedirect("servlet?login");
			} else {
				String loggedIn = (String) session.getAttribute("LogIn");

				if (loggedIn == null || !loggedIn.equals("yes"))
					response.sendRedirect("servlet?login");
				List<User> users = userRepository.findAll();
				out.print("<center>");
				out.println("<h1>ALL USERS</h1>");
				out.print("<table>");
				out.print(
						"<tr><th>ID</th><th>FIRST NAME</th><th>LAST NAME</th><th>USER NAME</th><th>PASSWORD</th></tr>");
				for (User u : users) {
					out.print("<tr><td><b>" + u.getId() + "</b></td><td>" + u.getFirstname() + "</td><td>"
							+ u.getLastname() + "</td><td>" + u.getUsername() + "</td><td>" + u.getPassword() + "</td>"
							+ "<td><a href=servlet?register&edit&id=" + u.getId() + ">Edit</a></td>");
					out.print("<form method='POST' action='servlet'>");
					out.print("<input type='hidden' name='delete' value=" + u.getId() + ">");
					out.print("<td><input type='submit' value='Delete'></td></tr>");
					out.print("</form>");
				}
				out.print("</table>");
				out.print("<hr><a href='servlet?register'>ADD USER</a>");
				out.print("</center>");
			}

		}

		else if (request.getParameter("login") != null) { // (request.getParameter("login")
															// != null)
			out.print("<center>");
			out.print("<h1>Authentification</h1>");
			out.print("<i style=\"color:red\">");
			if (request.getParameter("message") == null) {
				out.println("");
			} else if (request.getParameter("message").equals("0")) {
				out.println("incorrect authentification");
			}
			out.print("</i>");
			out.print("<table><form action='servlet' method='POST'>");
			out.print("<input type='hidden' name='login'>");
			out.print("<tr><td>USERNAME</td><td><input type='text' name='uname'></td></tr>");
			out.print("<tr><td>PASSWORD</td><td><input type='password' name='passw'></td></tr>");
			out.print("<tr><td></td><td><input type='submit' value='OK'></td></tr>");
			out.print("</center>");
		}

		out.print("</body><html>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("register") != null) {

			if (request.getParameter("edit") != null && request.getParameter("id") != null) {
				int id = Integer.parseInt(request.getParameter("id"));
				User uToUpdate = userRepository.findOne(id);
				uToUpdate.setFirstname(request.getParameter("fname"));
				uToUpdate.setLastname(request.getParameter("lname"));
				uToUpdate.setUsername(request.getParameter("uname"));
				uToUpdate.setPassword(request.getParameter("passw"));
				userRepository.updateUser(uToUpdate);
				response.sendRedirect("servlet?all");

			} else {
				userRepository.addUser(new User(request.getParameter("fname"), request.getParameter("lname"),
						request.getParameter("uname"), request.getParameter("passw")));
				response.sendRedirect("servlet?all");

			}
		} else if (request.getParameter("login") != null) {
			if (userRepository.isOK(request.getParameter("uname"), request.getParameter("passw"))) {
				System.out.println("oui");

				HttpSession session = request.getSession(true);
				session.setAttribute("LogIn", "yes");

				response.sendRedirect("servlet?all");
			} else {
				System.out.println("non");
				response.sendRedirect("servlet?login&message=0");

			}
		} else if (request.getParameter("delete") != null) {
			int id = Integer.parseInt(request.getParameter("delete"));
			userRepository.delete(id);
			response.sendRedirect("servlet?all");
		}
	}

}
