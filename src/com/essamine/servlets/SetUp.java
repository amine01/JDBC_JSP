package com.essamine.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.essamine.repositories.UserRepository;


public class SetUp implements ServletContextListener  {
	

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		UserRepository userRepository=new UserRepository();
		userRepository.init();
	}

}
