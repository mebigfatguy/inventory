package com.mebigfatguy.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleServlet extends HttpServlet {

    private static final long serialVersionUID = 9020043284177217643L;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try (PrintWriter pw = resp.getWriter()) {
            pw.println("It works");
        }
    }
}
