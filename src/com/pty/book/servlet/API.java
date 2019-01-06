package com.pty.book.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pty.book.dao.BookDao;

/**
 * Servlet implementation class API
 */
@WebServlet("/API/*")
public class API extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public API() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String booksjson;
		try {
			booksjson = BookDao.QUERY();
			out.write(booksjson);
			out.flush();
			out.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/plain");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String result;

		if (request.getRequestURI().equals("/MyBooks/API/INSERT")) {
			System.out.println("insert");
			BufferedReader reader = request.getReader();
			String json = reader.readLine();
			System.out.println(json);
			reader.close();
			try {
				result = BookDao.INSERT(json);
				out.write(result);
				out.flush();
				out.close();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (request.getRequestURI().equals("/MyBooks/API/DELETE")) {
			System.out.println("delete");
			String Bid = request.getParameter("Bid");
			System.out.println(Bid);
			try {
				result = BookDao.DELETE(Bid);
				out.write(result);
				out.flush();
				out.close();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (request.getRequestURI().equals("/MyBooks/API/UPDATE")) {
			System.out.println("update");

			BufferedReader reader = request.getReader();
			String json = reader.readLine();
			System.out.println(json);
			reader.close();
			try {
				result = BookDao.UPDATE(json);
				out.write(result);
				out.flush();
				out.close();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
