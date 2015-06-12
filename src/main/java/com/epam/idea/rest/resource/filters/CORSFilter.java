package com.epam.idea.rest.resource.filters;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * Created by Ihar_Niakhlebau on 04-Jun-15.
 */
@Component
public class CORSFilter {
//
//		public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//			HttpServletResponse response = (HttpServletResponse) res;
//			response.setHeader("Access-Control-Allow-Origin", "*");
//			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//			response.setHeader("Access-Control-Max-Age", "3600");
//			response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//			chain.doFilter(req, res);
//		}
//
//		public void init(FilterConfig filterConfig) {}
//
//		public void destroy() {}
//
//
//	@Override
//	public boolean isLoggable(LogRecord record) {
//		return false;
//	}
}
