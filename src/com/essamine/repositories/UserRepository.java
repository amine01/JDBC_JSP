package com.essamine.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.essamine.entities.User;

public class UserRepository {
	private Connection cnt;
	// private Statement stmt;

	public UserRepository() {
		// establish connection with the db
		// //jdbc:hsqldb:file:E:\Workspace_sts\helloProjectWeb\target\data\
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			// org.hsqldb.jdbcDriver
			cnt = DriverManager.getConnection("jdbc:hsqldb:file:E:/Workspace_sts/jsp_jdbc/data/bd_user", "SA", "");
		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		}
	}

	public void init() {
		Statement stmt;

		try {
			stmt = cnt.createStatement();
			String create_table = "CREATE TABLE IF NOT EXISTS user(id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1), FIRSTNAME VARCHAR(30),LASTNAME VARCHAR(30),USERNAME VARCHAR(30),PASSWORD VARCHAR(30))";
			stmt.executeUpdate(create_table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addUser(User user) {
		Statement stmt;
		try {
			stmt = cnt.createStatement();
			stmt.executeUpdate("insert into user(FIRSTNAME,LASTNAME,USERNAME,PASSWORD) values('" + user.getFirstname()
					+ "','" + user.getLastname() + "','" + user.getUsername() + "','" + user.getPassword() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> findAll() {
		Statement stmt;
		List<User> users = null;
		try {
			stmt = cnt.createStatement();
			ResultSet rs = stmt.executeQuery("select * from user");
			users = new ArrayList<>();
			while (rs.next()) {
				users.add(new User(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5)));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}

	public void delete(int id) {
		Statement stmt;
		try {
			stmt = cnt.createStatement();
			String sql = "delete from user where id=" + id;
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public User findOne(int id) {
		User u = new User();

		try {
			ResultSet rs = cnt.createStatement().executeQuery("select * from user where id=" + id);
			while (rs.next()) {
				u.setId(id);
				u.setFirstname(rs.getString(2));
				u.setLastname(rs.getString(3));
				u.setUsername(rs.getString(4));
				u.setPassword(rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}

	public void updateUser(User u) {
		try {
			cnt.createStatement()
					.executeUpdate("update user set firstname='" + u.getFirstname() + "',lastname='" + u.getLastname()
							+ "', username='" + u.getUsername() + "', password='" + u.getPassword() + "' where id="
							+ u.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isOK(String username, String password) {
		Statement stmt;
		try {
			stmt = cnt.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select username from user where username='" + username + "' and password='" + password + "'");
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}