package com.essamine.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse arg1, FilterChain filterChain)
			throws IOException, ServletException {
		
		System.out.println(request.getRemoteHost());
		filterChain.doFilter(request, arg1);
	}

	@Override
	public void init(FilterConfig fC) throws ServletException {

	}

}
