package com.mebigfatguy.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleServlet extends HttpServlet {

	private Logger LOGGER = LoggerFactory.getLogger(SimpleServlet.class);
	
    private static final long serialVersionUID = 9020043284177217643L;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	LocalDateTime start = LocalDateTime.now();
    	try {
		    try (PrintWriter pw = resp.getWriter()) {
		        pw.println("It works");
		    }
    	} finally {
    		LocalDateTime end = LocalDateTime.now();
    		LOGGER.info("doGet finished in {} seconds", Duration.between(start,  end).getSeconds());
    	}
    }
}
